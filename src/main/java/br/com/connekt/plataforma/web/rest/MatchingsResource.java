package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.MatchingsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.MatchingsDTO;
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
 * REST controller for managing Matchings.
 */
@RestController
@RequestMapping("/api")
public class MatchingsResource {

    private final Logger log = LoggerFactory.getLogger(MatchingsResource.class);

    private static final String ENTITY_NAME = "matchings";

    private final MatchingsService matchingsService;

    public MatchingsResource(MatchingsService matchingsService) {
        this.matchingsService = matchingsService;
    }

    /**
     * POST  /matchings : Create a new matchings.
     *
     * @param matchingsDTO the matchingsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matchingsDTO, or with status 400 (Bad Request) if the matchings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matchings")
    @Timed
    public ResponseEntity<MatchingsDTO> createMatchings(@RequestBody MatchingsDTO matchingsDTO) throws URISyntaxException {
        log.debug("REST request to save Matchings : {}", matchingsDTO);
        if (matchingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new matchings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatchingsDTO result = matchingsService.save(matchingsDTO);
        return ResponseEntity.created(new URI("/api/matchings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /matchings : Updates an existing matchings.
     *
     * @param matchingsDTO the matchingsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matchingsDTO,
     * or with status 400 (Bad Request) if the matchingsDTO is not valid,
     * or with status 500 (Internal Server Error) if the matchingsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matchings")
    @Timed
    public ResponseEntity<MatchingsDTO> updateMatchings(@RequestBody MatchingsDTO matchingsDTO) throws URISyntaxException {
        log.debug("REST request to update Matchings : {}", matchingsDTO);
        if (matchingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatchingsDTO result = matchingsService.save(matchingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matchingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /matchings : get all the matchings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of matchings in body
     */
    @GetMapping("/matchings")
    @Timed
    public ResponseEntity<List<MatchingsDTO>> getAllMatchings(Pageable pageable) {
        log.debug("REST request to get a page of Matchings");
        Page<MatchingsDTO> page = matchingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/matchings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /matchings/:id : get the "id" matchings.
     *
     * @param id the id of the matchingsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matchingsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/matchings/{id}")
    @Timed
    public ResponseEntity<MatchingsDTO> getMatchings(@PathVariable Long id) {
        log.debug("REST request to get Matchings : {}", id);
        Optional<MatchingsDTO> matchingsDTO = matchingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matchingsDTO);
    }

    /**
     * DELETE  /matchings/:id : delete the "id" matchings.
     *
     * @param id the id of the matchingsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matchings/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatchings(@PathVariable Long id) {
        log.debug("REST request to delete Matchings : {}", id);
        matchingsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/matchings?query=:query : search for the matchings corresponding
     * to the query.
     *
     * @param query the query of the matchings search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/matchings")
    @Timed
    public ResponseEntity<List<MatchingsDTO>> searchMatchings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Matchings for query {}", query);
        Page<MatchingsDTO> page = matchingsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/matchings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
