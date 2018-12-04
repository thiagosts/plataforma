package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.ResourcesService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.ResourcesDTO;
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
 * REST controller for managing Resources.
 */
@RestController
@RequestMapping("/api")
public class ResourcesResource {

    private final Logger log = LoggerFactory.getLogger(ResourcesResource.class);

    private static final String ENTITY_NAME = "resources";

    private final ResourcesService resourcesService;

    public ResourcesResource(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    /**
     * POST  /resources : Create a new resources.
     *
     * @param resourcesDTO the resourcesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resourcesDTO, or with status 400 (Bad Request) if the resources has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resources")
    @Timed
    public ResponseEntity<ResourcesDTO> createResources(@RequestBody ResourcesDTO resourcesDTO) throws URISyntaxException {
        log.debug("REST request to save Resources : {}", resourcesDTO);
        if (resourcesDTO.getId() != null) {
            throw new BadRequestAlertException("A new resources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourcesDTO result = resourcesService.save(resourcesDTO);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resources : Updates an existing resources.
     *
     * @param resourcesDTO the resourcesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resourcesDTO,
     * or with status 400 (Bad Request) if the resourcesDTO is not valid,
     * or with status 500 (Internal Server Error) if the resourcesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resources")
    @Timed
    public ResponseEntity<ResourcesDTO> updateResources(@RequestBody ResourcesDTO resourcesDTO) throws URISyntaxException {
        log.debug("REST request to update Resources : {}", resourcesDTO);
        if (resourcesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourcesDTO result = resourcesService.save(resourcesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resourcesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resources : get all the resources.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resources in body
     */
    @GetMapping("/resources")
    @Timed
    public ResponseEntity<List<ResourcesDTO>> getAllResources(Pageable pageable) {
        log.debug("REST request to get a page of Resources");
        Page<ResourcesDTO> page = resourcesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /resources/:id : get the "id" resources.
     *
     * @param id the id of the resourcesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resourcesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/resources/{id}")
    @Timed
    public ResponseEntity<ResourcesDTO> getResources(@PathVariable Long id) {
        log.debug("REST request to get Resources : {}", id);
        Optional<ResourcesDTO> resourcesDTO = resourcesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourcesDTO);
    }

    /**
     * DELETE  /resources/:id : delete the "id" resources.
     *
     * @param id the id of the resourcesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteResources(@PathVariable Long id) {
        log.debug("REST request to delete Resources : {}", id);
        resourcesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/resources?query=:query : search for the resources corresponding
     * to the query.
     *
     * @param query the query of the resources search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/resources")
    @Timed
    public ResponseEntity<List<ResourcesDTO>> searchResources(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Resources for query {}", query);
        Page<ResourcesDTO> page = resourcesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/resources");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
