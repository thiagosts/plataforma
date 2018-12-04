package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.ResultsDetailsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.ResultsDetailsDTO;
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
 * REST controller for managing ResultsDetails.
 */
@RestController
@RequestMapping("/api")
public class ResultsDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ResultsDetailsResource.class);

    private static final String ENTITY_NAME = "resultsDetails";

    private final ResultsDetailsService resultsDetailsService;

    public ResultsDetailsResource(ResultsDetailsService resultsDetailsService) {
        this.resultsDetailsService = resultsDetailsService;
    }

    /**
     * POST  /results-details : Create a new resultsDetails.
     *
     * @param resultsDetailsDTO the resultsDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultsDetailsDTO, or with status 400 (Bad Request) if the resultsDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/results-details")
    @Timed
    public ResponseEntity<ResultsDetailsDTO> createResultsDetails(@RequestBody ResultsDetailsDTO resultsDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ResultsDetails : {}", resultsDetailsDTO);
        if (resultsDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultsDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultsDetailsDTO result = resultsDetailsService.save(resultsDetailsDTO);
        return ResponseEntity.created(new URI("/api/results-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /results-details : Updates an existing resultsDetails.
     *
     * @param resultsDetailsDTO the resultsDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultsDetailsDTO,
     * or with status 400 (Bad Request) if the resultsDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the resultsDetailsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/results-details")
    @Timed
    public ResponseEntity<ResultsDetailsDTO> updateResultsDetails(@RequestBody ResultsDetailsDTO resultsDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ResultsDetails : {}", resultsDetailsDTO);
        if (resultsDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultsDetailsDTO result = resultsDetailsService.save(resultsDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultsDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /results-details : get all the resultsDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resultsDetails in body
     */
    @GetMapping("/results-details")
    @Timed
    public ResponseEntity<List<ResultsDetailsDTO>> getAllResultsDetails(Pageable pageable) {
        log.debug("REST request to get a page of ResultsDetails");
        Page<ResultsDetailsDTO> page = resultsDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/results-details");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /results-details/:id : get the "id" resultsDetails.
     *
     * @param id the id of the resultsDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultsDetailsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/results-details/{id}")
    @Timed
    public ResponseEntity<ResultsDetailsDTO> getResultsDetails(@PathVariable Long id) {
        log.debug("REST request to get ResultsDetails : {}", id);
        Optional<ResultsDetailsDTO> resultsDetailsDTO = resultsDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultsDetailsDTO);
    }

    /**
     * DELETE  /results-details/:id : delete the "id" resultsDetails.
     *
     * @param id the id of the resultsDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/results-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteResultsDetails(@PathVariable Long id) {
        log.debug("REST request to delete ResultsDetails : {}", id);
        resultsDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/results-details?query=:query : search for the resultsDetails corresponding
     * to the query.
     *
     * @param query the query of the resultsDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/results-details")
    @Timed
    public ResponseEntity<List<ResultsDetailsDTO>> searchResultsDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ResultsDetails for query {}", query);
        Page<ResultsDetailsDTO> page = resultsDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/results-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
