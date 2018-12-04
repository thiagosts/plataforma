package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.OpportunitiesService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.OpportunitiesDTO;
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
 * REST controller for managing Opportunities.
 */
@RestController
@RequestMapping("/api")
public class OpportunitiesResource {

    private final Logger log = LoggerFactory.getLogger(OpportunitiesResource.class);

    private static final String ENTITY_NAME = "opportunities";

    private final OpportunitiesService opportunitiesService;

    public OpportunitiesResource(OpportunitiesService opportunitiesService) {
        this.opportunitiesService = opportunitiesService;
    }

    /**
     * POST  /opportunities : Create a new opportunities.
     *
     * @param opportunitiesDTO the opportunitiesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new opportunitiesDTO, or with status 400 (Bad Request) if the opportunities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/opportunities")
    @Timed
    public ResponseEntity<OpportunitiesDTO> createOpportunities(@RequestBody OpportunitiesDTO opportunitiesDTO) throws URISyntaxException {
        log.debug("REST request to save Opportunities : {}", opportunitiesDTO);
        if (opportunitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new opportunities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpportunitiesDTO result = opportunitiesService.save(opportunitiesDTO);
        return ResponseEntity.created(new URI("/api/opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /opportunities : Updates an existing opportunities.
     *
     * @param opportunitiesDTO the opportunitiesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated opportunitiesDTO,
     * or with status 400 (Bad Request) if the opportunitiesDTO is not valid,
     * or with status 500 (Internal Server Error) if the opportunitiesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/opportunities")
    @Timed
    public ResponseEntity<OpportunitiesDTO> updateOpportunities(@RequestBody OpportunitiesDTO opportunitiesDTO) throws URISyntaxException {
        log.debug("REST request to update Opportunities : {}", opportunitiesDTO);
        if (opportunitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OpportunitiesDTO result = opportunitiesService.save(opportunitiesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, opportunitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /opportunities : get all the opportunities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of opportunities in body
     */
    @GetMapping("/opportunities")
    @Timed
    public ResponseEntity<List<OpportunitiesDTO>> getAllOpportunities(Pageable pageable) {
        log.debug("REST request to get a page of Opportunities");
        Page<OpportunitiesDTO> page = opportunitiesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/opportunities");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /opportunities/:id : get the "id" opportunities.
     *
     * @param id the id of the opportunitiesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the opportunitiesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/opportunities/{id}")
    @Timed
    public ResponseEntity<OpportunitiesDTO> getOpportunities(@PathVariable Long id) {
        log.debug("REST request to get Opportunities : {}", id);
        Optional<OpportunitiesDTO> opportunitiesDTO = opportunitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunitiesDTO);
    }

    /**
     * DELETE  /opportunities/:id : delete the "id" opportunities.
     *
     * @param id the id of the opportunitiesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/opportunities/{id}")
    @Timed
    public ResponseEntity<Void> deleteOpportunities(@PathVariable Long id) {
        log.debug("REST request to delete Opportunities : {}", id);
        opportunitiesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/opportunities?query=:query : search for the opportunities corresponding
     * to the query.
     *
     * @param query the query of the opportunities search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/opportunities")
    @Timed
    public ResponseEntity<List<OpportunitiesDTO>> searchOpportunities(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Opportunities for query {}", query);
        Page<OpportunitiesDTO> page = opportunitiesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/opportunities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
