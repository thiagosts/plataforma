package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.CustomersService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.CustomersDTO;
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
 * REST controller for managing Customers.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    private final CustomersService customersService;

    public CustomersResource(CustomersService customersService) {
        this.customersService = customersService;
    }

    /**
     * POST  /customers : Create a new customers.
     *
     * @param customersDTO the customersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customersDTO, or with status 400 (Bad Request) if the customers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customers")
    @Timed
    public ResponseEntity<CustomersDTO> createCustomers(@RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customersDTO);
        if (customersDTO.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customers : Updates an existing customers.
     *
     * @param customersDTO the customersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customersDTO,
     * or with status 400 (Bad Request) if the customersDTO is not valid,
     * or with status 500 (Internal Server Error) if the customersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customers")
    @Timed
    public ResponseEntity<CustomersDTO> updateCustomers(@RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to update Customers : {}", customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    @Timed
    public ResponseEntity<List<CustomersDTO>> getAllCustomers(Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<CustomersDTO> page = customersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /customers/:id : get the "id" customers.
     *
     * @param id the id of the customersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    @Timed
    public ResponseEntity<CustomersDTO> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<CustomersDTO> customersDTO = customersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersDTO);
    }

    /**
     * DELETE  /customers/:id : delete the "id" customers.
     *
     * @param id the id of the customersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/customers?query=:query : search for the customers corresponding
     * to the query.
     *
     * @param query the query of the customers search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/customers")
    @Timed
    public ResponseEntity<List<CustomersDTO>> searchCustomers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Customers for query {}", query);
        Page<CustomersDTO> page = customersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
