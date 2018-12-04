package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.PortalService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.PortalDTO;
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
 * REST controller for managing Portal.
 */
@RestController
@RequestMapping("/api")
public class PortalResource {

    private final Logger log = LoggerFactory.getLogger(PortalResource.class);

    private static final String ENTITY_NAME = "portal";

    private final PortalService portalService;

    public PortalResource(PortalService portalService) {
        this.portalService = portalService;
    }

    /**
     * POST  /portals : Create a new portal.
     *
     * @param portalDTO the portalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portalDTO, or with status 400 (Bad Request) if the portal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/portals")
    @Timed
    public ResponseEntity<PortalDTO> createPortal(@RequestBody PortalDTO portalDTO) throws URISyntaxException {
        log.debug("REST request to save Portal : {}", portalDTO);
        if (portalDTO.getId() != null) {
            throw new BadRequestAlertException("A new portal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PortalDTO result = portalService.save(portalDTO);
        return ResponseEntity.created(new URI("/api/portals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portals : Updates an existing portal.
     *
     * @param portalDTO the portalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portalDTO,
     * or with status 400 (Bad Request) if the portalDTO is not valid,
     * or with status 500 (Internal Server Error) if the portalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/portals")
    @Timed
    public ResponseEntity<PortalDTO> updatePortal(@RequestBody PortalDTO portalDTO) throws URISyntaxException {
        log.debug("REST request to update Portal : {}", portalDTO);
        if (portalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PortalDTO result = portalService.save(portalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, portalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portals : get all the portals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of portals in body
     */
    @GetMapping("/portals")
    @Timed
    public ResponseEntity<List<PortalDTO>> getAllPortals(Pageable pageable) {
        log.debug("REST request to get a page of Portals");
        Page<PortalDTO> page = portalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/portals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /portals/:id : get the "id" portal.
     *
     * @param id the id of the portalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/portals/{id}")
    @Timed
    public ResponseEntity<PortalDTO> getPortal(@PathVariable Long id) {
        log.debug("REST request to get Portal : {}", id);
        Optional<PortalDTO> portalDTO = portalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(portalDTO);
    }

    /**
     * DELETE  /portals/:id : delete the "id" portal.
     *
     * @param id the id of the portalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/portals/{id}")
    @Timed
    public ResponseEntity<Void> deletePortal(@PathVariable Long id) {
        log.debug("REST request to delete Portal : {}", id);
        portalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/portals?query=:query : search for the portal corresponding
     * to the query.
     *
     * @param query the query of the portal search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/portals")
    @Timed
    public ResponseEntity<List<PortalDTO>> searchPortals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Portals for query {}", query);
        Page<PortalDTO> page = portalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/portals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
