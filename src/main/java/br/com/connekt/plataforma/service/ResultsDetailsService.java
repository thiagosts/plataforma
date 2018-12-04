package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.ResultsDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ResultsDetails.
 */
public interface ResultsDetailsService {

    /**
     * Save a resultsDetails.
     *
     * @param resultsDetailsDTO the entity to save
     * @return the persisted entity
     */
    ResultsDetailsDTO save(ResultsDetailsDTO resultsDetailsDTO);

    /**
     * Get all the resultsDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResultsDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resultsDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResultsDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" resultsDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the resultsDetails corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResultsDetailsDTO> search(String query, Pageable pageable);
}
