package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.CandidatesService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.CandidatesDTO;
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
 * REST controller for managing Candidates.
 */
@RestController
@RequestMapping("/api")
public class CandidatesResource {

    private final Logger log = LoggerFactory.getLogger(CandidatesResource.class);

    private static final String ENTITY_NAME = "candidates";

    private final CandidatesService candidatesService;

    public CandidatesResource(CandidatesService candidatesService) {
        this.candidatesService = candidatesService;
    }

    /**
     * POST  /candidates : Create a new candidates.
     *
     * @param candidatesDTO the candidatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidatesDTO, or with status 400 (Bad Request) if the candidates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidates")
    @Timed
    public ResponseEntity<CandidatesDTO> createCandidates(@RequestBody CandidatesDTO candidatesDTO) throws URISyntaxException {
        log.debug("REST request to save Candidates : {}", candidatesDTO);
        if (candidatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidatesDTO result = candidatesService.save(candidatesDTO);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidates : Updates an existing candidates.
     *
     * @param candidatesDTO the candidatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidatesDTO,
     * or with status 400 (Bad Request) if the candidatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the candidatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidates")
    @Timed
    public ResponseEntity<CandidatesDTO> updateCandidates(@RequestBody CandidatesDTO candidatesDTO) throws URISyntaxException {
        log.debug("REST request to update Candidates : {}", candidatesDTO);
        if (candidatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CandidatesDTO result = candidatesService.save(candidatesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidates : get all the candidates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candidates in body
     */
    @GetMapping("/candidates")
    @Timed
    public ResponseEntity<List<CandidatesDTO>> getAllCandidates(Pageable pageable) {
        log.debug("REST request to get a page of Candidates");
        Page<CandidatesDTO> page = candidatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidates");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /candidates/:id : get the "id" candidates.
     *
     * @param id the id of the candidatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<CandidatesDTO> getCandidates(@PathVariable Long id) {
        log.debug("REST request to get Candidates : {}", id);
        Optional<CandidatesDTO> candidatesDTO = candidatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidatesDTO);
    }

    /**
     * DELETE  /candidates/:id : delete the "id" candidates.
     *
     * @param id the id of the candidatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidates(@PathVariable Long id) {
        log.debug("REST request to delete Candidates : {}", id);
        candidatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/candidates?query=:query : search for the candidates corresponding
     * to the query.
     *
     * @param query the query of the candidates search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/candidates")
    @Timed
    public ResponseEntity<List<CandidatesDTO>> searchCandidates(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Candidates for query {}", query);
        Page<CandidatesDTO> page = candidatesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/candidates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
