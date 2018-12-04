package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.PlacesService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.PlacesDTO;
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
 * REST controller for managing Places.
 */
@RestController
@RequestMapping("/api")
public class PlacesResource {

    private final Logger log = LoggerFactory.getLogger(PlacesResource.class);

    private static final String ENTITY_NAME = "places";

    private final PlacesService placesService;

    public PlacesResource(PlacesService placesService) {
        this.placesService = placesService;
    }

    /**
     * POST  /places : Create a new places.
     *
     * @param placesDTO the placesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placesDTO, or with status 400 (Bad Request) if the places has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/places")
    @Timed
    public ResponseEntity<PlacesDTO> createPlaces(@RequestBody PlacesDTO placesDTO) throws URISyntaxException {
        log.debug("REST request to save Places : {}", placesDTO);
        if (placesDTO.getId() != null) {
            throw new BadRequestAlertException("A new places cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlacesDTO result = placesService.save(placesDTO);
        return ResponseEntity.created(new URI("/api/places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /places : Updates an existing places.
     *
     * @param placesDTO the placesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placesDTO,
     * or with status 400 (Bad Request) if the placesDTO is not valid,
     * or with status 500 (Internal Server Error) if the placesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/places")
    @Timed
    public ResponseEntity<PlacesDTO> updatePlaces(@RequestBody PlacesDTO placesDTO) throws URISyntaxException {
        log.debug("REST request to update Places : {}", placesDTO);
        if (placesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlacesDTO result = placesService.save(placesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /places : get all the places.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places")
    @Timed
    public ResponseEntity<List<PlacesDTO>> getAllPlaces(Pageable pageable) {
        log.debug("REST request to get a page of Places");
        Page<PlacesDTO> page = placesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/places");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /places/:id : get the "id" places.
     *
     * @param id the id of the placesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/places/{id}")
    @Timed
    public ResponseEntity<PlacesDTO> getPlaces(@PathVariable Long id) {
        log.debug("REST request to get Places : {}", id);
        Optional<PlacesDTO> placesDTO = placesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placesDTO);
    }

    /**
     * DELETE  /places/:id : delete the "id" places.
     *
     * @param id the id of the placesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/places/{id}")
    @Timed
    public ResponseEntity<Void> deletePlaces(@PathVariable Long id) {
        log.debug("REST request to delete Places : {}", id);
        placesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/places?query=:query : search for the places corresponding
     * to the query.
     *
     * @param query the query of the places search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/places")
    @Timed
    public ResponseEntity<List<PlacesDTO>> searchPlaces(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Places for query {}", query);
        Page<PlacesDTO> page = placesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/places");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
