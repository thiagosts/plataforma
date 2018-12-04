package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.MatchingsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Matchings.
 */
public interface MatchingsService {

    /**
     * Save a matchings.
     *
     * @param matchingsDTO the entity to save
     * @return the persisted entity
     */
    MatchingsDTO save(MatchingsDTO matchingsDTO);

    /**
     * Get all the matchings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MatchingsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" matchings.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MatchingsDTO> findOne(Long id);

    /**
     * Delete the "id" matchings.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the matchings corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MatchingsDTO> search(String query, Pageable pageable);
}
