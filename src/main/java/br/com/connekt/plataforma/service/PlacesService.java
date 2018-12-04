package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.PlacesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Places.
 */
public interface PlacesService {

    /**
     * Save a places.
     *
     * @param placesDTO the entity to save
     * @return the persisted entity
     */
    PlacesDTO save(PlacesDTO placesDTO);

    /**
     * Get all the places.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlacesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" places.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlacesDTO> findOne(Long id);

    /**
     * Delete the "id" places.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the places corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlacesDTO> search(String query, Pageable pageable);
}
