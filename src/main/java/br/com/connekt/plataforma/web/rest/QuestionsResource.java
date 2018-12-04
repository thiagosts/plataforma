package br.com.connekt.plataforma.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.connekt.plataforma.service.QuestionsService;
import br.com.connekt.plataforma.web.rest.errors.BadRequestAlertException;
import br.com.connekt.plataforma.web.rest.util.HeaderUtil;
import br.com.connekt.plataforma.web.rest.util.PaginationUtil;
import br.com.connekt.plataforma.service.dto.QuestionsDTO;
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
 * REST controller for managing Questions.
 */
@RestController
@RequestMapping("/api")
public class QuestionsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsResource.class);

    private static final String ENTITY_NAME = "questions";

    private final QuestionsService questionsService;

    public QuestionsResource(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    /**
     * POST  /questions : Create a new questions.
     *
     * @param questionsDTO the questionsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionsDTO, or with status 400 (Bad Request) if the questions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questions")
    @Timed
    public ResponseEntity<QuestionsDTO> createQuestions(@RequestBody QuestionsDTO questionsDTO) throws URISyntaxException {
        log.debug("REST request to save Questions : {}", questionsDTO);
        if (questionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new questions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionsDTO result = questionsService.save(questionsDTO);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questions : Updates an existing questions.
     *
     * @param questionsDTO the questionsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionsDTO,
     * or with status 400 (Bad Request) if the questionsDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questions")
    @Timed
    public ResponseEntity<QuestionsDTO> updateQuestions(@RequestBody QuestionsDTO questionsDTO) throws URISyntaxException {
        log.debug("REST request to update Questions : {}", questionsDTO);
        if (questionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionsDTO result = questionsService.save(questionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questions : get all the questions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of questions in body
     */
    @GetMapping("/questions")
    @Timed
    public ResponseEntity<List<QuestionsDTO>> getAllQuestions(Pageable pageable) {
        log.debug("REST request to get a page of Questions");
        Page<QuestionsDTO> page = questionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /questions/:id : get the "id" questions.
     *
     * @param id the id of the questionsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questions/{id}")
    @Timed
    public ResponseEntity<QuestionsDTO> getQuestions(@PathVariable Long id) {
        log.debug("REST request to get Questions : {}", id);
        Optional<QuestionsDTO> questionsDTO = questionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionsDTO);
    }

    /**
     * DELETE  /questions/:id : delete the "id" questions.
     *
     * @param id the id of the questionsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestions(@PathVariable Long id) {
        log.debug("REST request to delete Questions : {}", id);
        questionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/questions?query=:query : search for the questions corresponding
     * to the query.
     *
     * @param query the query of the questions search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/questions")
    @Timed
    public ResponseEntity<List<QuestionsDTO>> searchQuestions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Questions for query {}", query);
        Page<QuestionsDTO> page = questionsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
