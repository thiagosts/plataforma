package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.ResultsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Results.
 */
public interface ResultsService {

    /**
     * Save a results.
     *
     * @param resultsDTO the entity to save
     * @return the persisted entity
     */
    ResultsDTO save(ResultsDTO resultsDTO);

    /**
     * Get all the results.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResultsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" results.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResultsDTO> findOne(Long id);

    /**
     * Delete the "id" results.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the results corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResultsDTO> search(String query, Pageable pageable);
}
