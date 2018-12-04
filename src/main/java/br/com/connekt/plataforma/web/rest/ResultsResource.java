package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.ResultsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.ResultsDTO;
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
 * REST controller for managing Results.
 */
@RestController
@RequestMapping("/api")
public class ResultsResource {

    private final Logger log = LoggerFactory.getLogger(ResultsResource.class);

    private static final String ENTITY_NAME = "results";

    private final ResultsService resultsService;

    public ResultsResource(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    /**
     * POST  /results : Create a new results.
     *
     * @param resultsDTO the resultsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resultsDTO, or with status 400 (Bad Request) if the results has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/results")
    @Timed
    public ResponseEntity<ResultsDTO> createResults(@RequestBody ResultsDTO resultsDTO) throws URISyntaxException {
        log.debug("REST request to save Results : {}", resultsDTO);
        if (resultsDTO.getId() != null) {
            throw new BadRequestAlertException("A new results cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultsDTO result = resultsService.save(resultsDTO);
        return ResponseEntity.created(new URI("/api/results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /results : Updates an existing results.
     *
     * @param resultsDTO the resultsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resultsDTO,
     * or with status 400 (Bad Request) if the resultsDTO is not valid,
     * or with status 500 (Internal Server Error) if the resultsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/results")
    @Timed
    public ResponseEntity<ResultsDTO> updateResults(@RequestBody ResultsDTO resultsDTO) throws URISyntaxException {
        log.debug("REST request to update Results : {}", resultsDTO);
        if (resultsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultsDTO result = resultsService.save(resultsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resultsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /results : get all the results.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of results in body
     */
    @GetMapping("/results")
    @Timed
    public ResponseEntity<List<ResultsDTO>> getAllResults(Pageable pageable) {
        log.debug("REST request to get a page of Results");
        Page<ResultsDTO> page = resultsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/results");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /results/:id : get the "id" results.
     *
     * @param id the id of the resultsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resultsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/results/{id}")
    @Timed
    public ResponseEntity<ResultsDTO> getResults(@PathVariable Long id) {
        log.debug("REST request to get Results : {}", id);
        Optional<ResultsDTO> resultsDTO = resultsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultsDTO);
    }

    /**
     * DELETE  /results/:id : delete the "id" results.
     *
     * @param id the id of the resultsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/results/{id}")
    @Timed
    public ResponseEntity<Void> deleteResults(@PathVariable Long id) {
        log.debug("REST request to delete Results : {}", id);
        resultsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/results?query=:query : search for the results corresponding
     * to the query.
     *
     * @param query the query of the results search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/results")
    @Timed
    public ResponseEntity<List<ResultsDTO>> searchResults(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Results for query {}", query);
        Page<ResultsDTO> page = resultsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
