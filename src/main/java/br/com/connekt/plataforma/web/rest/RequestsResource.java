package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.RequestsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.RequestsDTO;
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
 * REST controller for managing Requests.
 */
@RestController
@RequestMapping("/api")
public class RequestsResource {

    private final Logger log = LoggerFactory.getLogger(RequestsResource.class);

    private static final String ENTITY_NAME = "requests";

    private final RequestsService requestsService;

    public RequestsResource(RequestsService requestsService) {
        this.requestsService = requestsService;
    }

    /**
     * POST  /requests : Create a new requests.
     *
     * @param requestsDTO the requestsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestsDTO, or with status 400 (Bad Request) if the requests has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/requests")
    @Timed
    public ResponseEntity<RequestsDTO> createRequests(@RequestBody RequestsDTO requestsDTO) throws URISyntaxException {
        log.debug("REST request to save Requests : {}", requestsDTO);
        if (requestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new requests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequestsDTO result = requestsService.save(requestsDTO);
        return ResponseEntity.created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /requests : Updates an existing requests.
     *
     * @param requestsDTO the requestsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestsDTO,
     * or with status 400 (Bad Request) if the requestsDTO is not valid,
     * or with status 500 (Internal Server Error) if the requestsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/requests")
    @Timed
    public ResponseEntity<RequestsDTO> updateRequests(@RequestBody RequestsDTO requestsDTO) throws URISyntaxException {
        log.debug("REST request to update Requests : {}", requestsDTO);
        if (requestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestsDTO result = requestsService.save(requestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /requests : get all the requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of requests in body
     */
    @GetMapping("/requests")
    @Timed
    public ResponseEntity<List<RequestsDTO>> getAllRequests(Pageable pageable) {
        log.debug("REST request to get a page of Requests");
        Page<RequestsDTO> page = requestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /requests/:id : get the "id" requests.
     *
     * @param id the id of the requestsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/requests/{id}")
    @Timed
    public ResponseEntity<RequestsDTO> getRequests(@PathVariable Long id) {
        log.debug("REST request to get Requests : {}", id);
        Optional<RequestsDTO> requestsDTO = requestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestsDTO);
    }

    /**
     * DELETE  /requests/:id : delete the "id" requests.
     *
     * @param id the id of the requestsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequests(@PathVariable Long id) {
        log.debug("REST request to delete Requests : {}", id);
        requestsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/requests?query=:query : search for the requests corresponding
     * to the query.
     *
     * @param query the query of the requests search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/requests")
    @Timed
    public ResponseEntity<List<RequestsDTO>> searchRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Requests for query {}", query);
        Page<RequestsDTO> page = requestsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
