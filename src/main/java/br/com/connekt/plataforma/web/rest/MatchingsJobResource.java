package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.MatchingsJobService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.MatchingsJobDTO;
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
 * REST controller for managing MatchingsJob.
 */
@RestController
@RequestMapping("/api")
public class MatchingsJobResource {

    private final Logger log = LoggerFactory.getLogger(MatchingsJobResource.class);

    private static final String ENTITY_NAME = "matchingsJob";

    private final MatchingsJobService matchingsJobService;

    public MatchingsJobResource(MatchingsJobService matchingsJobService) {
        this.matchingsJobService = matchingsJobService;
    }

    /**
     * POST  /matchings-jobs : Create a new matchingsJob.
     *
     * @param matchingsJobDTO the matchingsJobDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchingsJobDTO, or with status 400 (Bad Request) if the matchingsJob has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matchings-jobs")
    @Timed
    public ResponseEntity<MatchingsJobDTO> createMatchingsJob(@RequestBody MatchingsJobDTO matchingsJobDTO) throws URISyntaxException {
        log.debug("REST request to save MatchingsJob : {}", matchingsJobDTO);
        if (matchingsJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchingsJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchingsJobDTO result = matchingsJobService.save(matchingsJobDTO);
        return ResponseEntity.created(new URI("/api/matchings-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /matchings-jobs : Updates an existing matchingsJob.
     *
     * @param matchingsJobDTO the matchingsJobDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchingsJobDTO,
     * or with status 400 (Bad Request) if the matchingsJobDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchingsJobDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matchings-jobs")
    @Timed
    public ResponseEntity<MatchingsJobDTO> updateMatchingsJob(@RequestBody MatchingsJobDTO matchingsJobDTO) throws URISyntaxException {
        log.debug("REST request to update MatchingsJob : {}", matchingsJobDTO);
        if (matchingsJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchingsJobDTO result = matchingsJobService.save(matchingsJobDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchingsJobDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /matchings-jobs : get all the matchingsJobs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of matchingsJobs in body
     */
    @GetMapping("/matchings-jobs")
    @Timed
    public ResponseEntity<List<MatchingsJobDTO>> getAllMatchingsJobs(Pageable pageable) {
        log.debug("REST request to get a page of MatchingsJobs");
        Page<MatchingsJobDTO> page = matchingsJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/matchings-jobs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /matchings-jobs/:id : get the "id" matchingsJob.
     *
     * @param id the id of the matchingsJobDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchingsJobDTO, or with status 404 (Not Found)
     */
    @GetMapping("/matchings-jobs/{id}")
    @Timed
    public ResponseEntity<MatchingsJobDTO> getMatchingsJob(@PathVariable Long id) {
        log.debug("REST request to get MatchingsJob : {}", id);
        Optional<MatchingsJobDTO> matchingsJobDTO = matchingsJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchingsJobDTO);
    }

    /**
     * DELETE  /matchings-jobs/:id : delete the "id" matchingsJob.
     *
     * @param id the id of the matchingsJobDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matchings-jobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchingsJob(@PathVariable Long id) {
        log.debug("REST request to delete MatchingsJob : {}", id);
        matchingsJobService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/matchings-jobs?query=:query : search for the matchingsJob corresponding
     * to the query.
     *
     * @param query the query of the matchingsJob search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/matchings-jobs")
    @Timed
    public ResponseEntity<List<MatchingsJobDTO>> searchMatchingsJobs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MatchingsJobs for query {}", query);
        Page<MatchingsJobDTO> page = matchingsJobService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/matchings-jobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
