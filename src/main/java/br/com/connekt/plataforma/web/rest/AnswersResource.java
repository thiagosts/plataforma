package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.AnswersService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.AnswersDTO;
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
 * REST controller for managing Answers.
 */
@RestController
@RequestMapping("/api")
public class AnswersResource {

    private final Logger log = LoggerFactory.getLogger(AnswersResource.class);

    private static final String ENTITY_NAME = "answers";

    private final AnswersService answersService;

    public AnswersResource(AnswersService answersService) {
        this.answersService = answersService;
    }

    /**
     * POST  /answers : Create a new answers.
     *
     * @param answersDTO the answersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new answersDTO, or with status 400 (Bad Request) if the answers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/answers")
    @Timed
    public ResponseEntity<AnswersDTO> createAnswers(@RequestBody AnswersDTO answersDTO) throws URISyntaxException {
        log.debug("REST request to save Answers : {}", answersDTO);
        if (answersDTO.getId() != null) {
            throw new BadRequestAlertException("A new answers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswersDTO result = answersService.save(answersDTO);
        return ResponseEntity.created(new URI("/api/answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /answers : Updates an existing answers.
     *
     * @param answersDTO the answersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated answersDTO,
     * or with status 400 (Bad Request) if the answersDTO is not valid,
     * or with status 500 (Internal Server Error) if the answersDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/answers")
    @Timed
    public ResponseEntity<AnswersDTO> updateAnswers(@RequestBody AnswersDTO answersDTO) throws URISyntaxException {
        log.debug("REST request to update Answers : {}", answersDTO);
        if (answersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswersDTO result = answersService.save(answersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, answersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /answers : get all the answers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of answers in body
     */
    @GetMapping("/answers")
    @Timed
    public ResponseEntity<List<AnswersDTO>> getAllAnswers(Pageable pageable) {
        log.debug("REST request to get a page of Answers");
        Page<AnswersDTO> page = answersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/answers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /answers/:id : get the "id" answers.
     *
     * @param id the id of the answersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the answersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/answers/{id}")
    @Timed
    public ResponseEntity<AnswersDTO> getAnswers(@PathVariable Long id) {
        log.debug("REST request to get Answers : {}", id);
        Optional<AnswersDTO> answersDTO = answersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answersDTO);
    }

    /**
     * DELETE  /answers/:id : delete the "id" answers.
     *
     * @param id the id of the answersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnswers(@PathVariable Long id) {
        log.debug("REST request to delete Answers : {}", id);
        answersService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/answers?query=:query : search for the answers corresponding
     * to the query.
     *
     * @param query the query of the answers search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/answers")
    @Timed
    public ResponseEntity<List<AnswersDTO>> searchAnswers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Answers for query {}", query);
        Page<AnswersDTO> page = answersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/answers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
