package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.CustomizationService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.CustomizationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Customization.
 */
@RestController
@RequestMapping("/api")
public class CustomizationResource {

    private final Logger log = LoggerFactory.getLogger(CustomizationResource.class);

    private static final String ENTITY_NAME = "customization";

    private final CustomizationService customizationService;

    public CustomizationResource(CustomizationService customizationService) {
        this.customizationService = customizationService;
    }

    /**
     * POST  /customizations : Create a new customization.
     *
     * @param customizationDTO the customizationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customizationDTO, or with status 400 (Bad Request) if the customization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customizations")
    @Timed
    public ResponseEntity<CustomizationDTO> createCustomization(@RequestBody CustomizationDTO customizationDTO) throws URISyntaxException {
        log.debug("REST request to save Customization : {}", customizationDTO);
        if (customizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new customization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomizationDTO result = customizationService.save(customizationDTO);
        return ResponseEntity.created(new URI("/api/customizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customizations : Updates an existing customization.
     *
     * @param customizationDTO the customizationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customizationDTO,
     * or with status 400 (Bad Request) if the customizationDTO is not valid,
     * or with status 500 (Internal Server Error) if the customizationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customizations")
    @Timed
    public ResponseEntity<CustomizationDTO> updateCustomization(@RequestBody CustomizationDTO customizationDTO) throws URISyntaxException {
        log.debug("REST request to update Customization : {}", customizationDTO);
        if (customizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomizationDTO result = customizationService.save(customizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customizations : get all the customizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customizations in body
     */
    @GetMapping("/customizations")
    @Timed
    public ResponseEntity<List<CustomizationDTO>> getAllCustomizations(Pageable pageable) {
        log.debug("REST request to get a page of Customizations");
        Page<CustomizationDTO> page = customizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customizations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /customizations/:id : get the "id" customization.
     *
     * @param id the id of the customizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customizations/{id}")
    @Timed
    public ResponseEntity<CustomizationDTO> getCustomization(@PathVariable Long id) {
        log.debug("REST request to get Customization : {}", id);
        Optional<CustomizationDTO> customizationDTO = customizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customizationDTO);
    }

    /**
     * DELETE  /customizations/:id : delete the "id" customization.
     *
     * @param id the id of the customizationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomization(@PathVariable Long id) {
        log.debug("REST request to delete Customization : {}", id);
        customizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customizations?query=:query : search for the customization corresponding
     * to the query.
     *
     * @param query the query of the customization search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/customizations")
    @Timed
    public ResponseEntity<List<CustomizationDTO>> searchCustomizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Customizations for query {}", query);
        Page<CustomizationDTO> page = customizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
