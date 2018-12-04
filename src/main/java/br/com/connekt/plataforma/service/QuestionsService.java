package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.QuestionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Questions.
 */
public interface QuestionsService {

    /**
     * Save a questions.
     *
     * @param questionsDTO the entity to save
     * @return the persisted entity
     */
    QuestionsDTO save(QuestionsDTO questionsDTO);

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" questions.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionsDTO> findOne(Long id);

    /**
     * Delete the "id" questions.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the questions corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<QuestionsDTO> search(String query, Pageable pageable);
}
