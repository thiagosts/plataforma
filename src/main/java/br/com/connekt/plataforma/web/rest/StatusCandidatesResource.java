package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.StatusCandidatesService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.StatusCandidatesDTO;
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
 * REST controller for managing StatusCandidates.
 */
@RestController
@RequestMapping("/api")
public class StatusCandidatesResource {

    private final Logger log = LoggerFactory.getLogger(StatusCandidatesResource.class);

    private static final String ENTITY_NAME = "statusCandidates";

    private final StatusCandidatesService statusCandidatesService;

    public StatusCandidatesResource(StatusCandidatesService statusCandidatesService) {
        this.statusCandidatesService = statusCandidatesService;
    }

    /**
     * POST  /status-candidates : Create a new statusCandidates.
     *
     * @param statusCandidatesDTO the statusCandidatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statusCandidatesDTO, or with status 400 (Bad Request) if the statusCandidates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/status-candidates")
    @Timed
    public ResponseEntity<StatusCandidatesDTO> createStatusCandidates(@RequestBody StatusCandidatesDTO statusCandidatesDTO) throws URISyntaxException {
        log.debug("REST request to save StatusCandidates : {}", statusCandidatesDTO);
        if (statusCandidatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusCandidates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusCandidatesDTO result = statusCandidatesService.save(statusCandidatesDTO);
        return ResponseEntity.created(new URI("/api/status-candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /status-candidates : Updates an existing statusCandidates.
     *
     * @param statusCandidatesDTO the statusCandidatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statusCandidatesDTO,
     * or with status 400 (Bad Request) if the statusCandidatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the statusCandidatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/status-candidates")
    @Timed
    public ResponseEntity<StatusCandidatesDTO> updateStatusCandidates(@RequestBody StatusCandidatesDTO statusCandidatesDTO) throws URISyntaxException {
        log.debug("REST request to update StatusCandidates : {}", statusCandidatesDTO);
        if (statusCandidatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusCandidatesDTO result = statusCandidatesService.save(statusCandidatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statusCandidatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /status-candidates : get all the statusCandidates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of statusCandidates in body
     */
    @GetMapping("/status-candidates")
    @Timed
    public ResponseEntity<List<StatusCandidatesDTO>> getAllStatusCandidates(Pageable pageable) {
        log.debug("REST request to get a page of StatusCandidates");
        Page<StatusCandidatesDTO> page = statusCandidatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/status-candidates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /status-candidates/:id : get the "id" statusCandidates.
     *
     * @param id the id of the statusCandidatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statusCandidatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/status-candidates/{id}")
    @Timed
    public ResponseEntity<StatusCandidatesDTO> getStatusCandidates(@PathVariable Long id) {
        log.debug("REST request to get StatusCandidates : {}", id);
        Optional<StatusCandidatesDTO> statusCandidatesDTO = statusCandidatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusCandidatesDTO);
    }

    /**
     * DELETE  /status-candidates/:id : delete the "id" statusCandidates.
     *
     * @param id the id of the statusCandidatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/status-candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatusCandidates(@PathVariable Long id) {
        log.debug("REST request to delete StatusCandidates : {}", id);
        statusCandidatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/status-candidates?query=:query : search for the statusCandidates corresponding
     * to the query.
     *
     * @param query the query of the statusCandidates search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/status-candidates")
    @Timed
    public ResponseEntity<List<StatusCandidatesDTO>> searchStatusCandidates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StatusCandidates for query {}", query);
        Page<StatusCandidatesDTO> page = statusCandidatesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/status-candidates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
