package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.QuestionsService;
import br.com.connekt.plataforma.domain.Questions;
import br.com.connekt.plataforma.repository.QuestionsRepository;
import br.com.connekt.plataforma.repository.search.QuestionsSearchRepository;
import br.com.connekt.plataforma.service.dto.QuestionsDTO;
import br.com.connekt.plataforma.service.mapper.QuestionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Questions.
 */
@Service
@Transactional
public class QuestionsServiceImpl implements QuestionsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsServiceImpl.class);

    private final QuestionsRepository questionsRepository;

    private final QuestionsMapper questionsMapper;

    private final QuestionsSearchRepository questionsSearchRepository;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository, QuestionsMapper questionsMapper, QuestionsSearchRepository questionsSearchRepository) {
        this.questionsRepository = questionsRepository;
        this.questionsMapper = questionsMapper;
        this.questionsSearchRepository = questionsSearchRepository;
    }

    /**
     * Save a questions.
     *
     * @param questionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionsDTO save(QuestionsDTO questionsDTO) {
        log.debug("Request to save Questions : {}", questionsDTO);

        Questions questions = questionsMapper.toEntity(questionsDTO);
        questions = questionsRepository.save(questions);
        QuestionsDTO result = questionsMapper.toDto(questions);
        questionsSearchRepository.save(questions);
        return result;
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionsRepository.findAll(pageable)
            .map(questionsMapper::toDto);
    }


    /**
     * Get one questions by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionsDTO> findOne(Long id) {
        log.debug("Request to get Questions : {}", id);
        return questionsRepository.findById(id)
            .map(questionsMapper::toDto);
    }

    /**
     * Delete the questions by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Questions : {}", id);
        questionsRepository.deleteById(id);
        questionsSearchRepository.deleteById(id);
    }

    /**
     * Search for the questions corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Questions for query {}", query);
        return questionsSearchRepository.search(queryStringQuery(query), pageable)
            .map(questionsMapper::toDto);
    }
}
