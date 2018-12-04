package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.OpportunitiesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Opportunities.
 */
public interface OpportunitiesService {

    /**
     * Save a opportunities.
     *
     * @param opportunitiesDTO the entity to save
     * @return the persisted entity
     */
    OpportunitiesDTO save(OpportunitiesDTO opportunitiesDTO);

    /**
     * Get all the opportunities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OpportunitiesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" opportunities.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OpportunitiesDTO> findOne(Long id);

    /**
     * Delete the "id" opportunities.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the opportunities corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OpportunitiesDTO> search(String query, Pageable pageable);
}
