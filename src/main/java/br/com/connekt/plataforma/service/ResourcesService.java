package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.ResourcesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Resources.
 */
public interface ResourcesService {

    /**
     * Save a resources.
     *
     * @param resourcesDTO the entity to save
     * @return the persisted entity
     */
    ResourcesDTO save(ResourcesDTO resourcesDTO);

    /**
     * Get all the resources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResourcesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resources.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResourcesDTO> findOne(Long id);

    /**
     * Delete the "id" resources.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the resources corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResourcesDTO> search(String query, Pageable pageable);
}
