package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.BenefitsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.BenefitsDTO;
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
 * REST controller for managing Benefits.
 */
@RestController
@RequestMapping("/api")
public class BenefitsResource {

    private final Logger log = LoggerFactory.getLogger(BenefitsResource.class);

    private static final String ENTITY_NAME = "benefits";

    private final BenefitsService benefitsService;

    public BenefitsResource(BenefitsService benefitsService) {
        this.benefitsService = benefitsService;
    }

    /**
     * POST  /benefits : Create a new benefits.
     *
     * @param benefitsDTO the benefitsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new benefitsDTO, or with status 400 (Bad Request) if the benefits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/benefits")
    @Timed
    public ResponseEntity<BenefitsDTO> createBenefits(@RequestBody BenefitsDTO benefitsDTO) throws URISyntaxException {
        log.debug("REST request to save Benefits : {}", benefitsDTO);
        if (benefitsDTO.getId() != null) {
            throw new BadRequestAlertException("A new benefits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitsDTO result = benefitsService.save(benefitsDTO);
        return ResponseEntity.created(new URI("/api/benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /benefits : Updates an existing benefits.
     *
     * @param benefitsDTO the benefitsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated benefitsDTO,
     * or with status 400 (Bad Request) if the benefitsDTO is not valid,
     * or with status 500 (Internal Server Error) if the benefitsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/benefits")
    @Timed
    public ResponseEntity<BenefitsDTO> updateBenefits(@RequestBody BenefitsDTO benefitsDTO) throws URISyntaxException {
        log.debug("REST request to update Benefits : {}", benefitsDTO);
        if (benefitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BenefitsDTO result = benefitsService.save(benefitsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, benefitsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /benefits : get all the benefits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of benefits in body
     */
    @GetMapping("/benefits")
    @Timed
    public ResponseEntity<List<BenefitsDTO>> getAllBenefits(Pageable pageable) {
        log.debug("REST request to get a page of Benefits");
        Page<BenefitsDTO> page = benefitsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/benefits");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /benefits/:id : get the "id" benefits.
     *
     * @param id the id of the benefitsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the benefitsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/benefits/{id}")
    @Timed
    public ResponseEntity<BenefitsDTO> getBenefits(@PathVariable Long id) {
        log.debug("REST request to get Benefits : {}", id);
        Optional<BenefitsDTO> benefitsDTO = benefitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitsDTO);
    }

    /**
     * DELETE  /benefits/:id : delete the "id" benefits.
     *
     * @param id the id of the benefitsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/benefits/{id}")
    @Timed
    public ResponseEntity<Void> deleteBenefits(@PathVariable Long id) {
        log.debug("REST request to delete Benefits : {}", id);
        benefitsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/benefits?query=:query : search for the benefits corresponding
     * to the query.
     *
     * @param query the query of the benefits search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/benefits")
    @Timed
    public ResponseEntity<List<BenefitsDTO>> searchBenefits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Benefits for query {}", query);
        Page<BenefitsDTO> page = benefitsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/benefits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
