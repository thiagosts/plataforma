package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.PreferencesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Preferences.
 */
public interface PreferencesService {

    /**
     * Save a preferences.
     *
     * @param preferencesDTO the entity to save
     * @return the persisted entity
     */
    PreferencesDTO save(PreferencesDTO preferencesDTO);

    /**
     * Get all the preferences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PreferencesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" preferences.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PreferencesDTO> findOne(Long id);

    /**
     * Delete the "id" preferences.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the preferences corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PreferencesDTO> search(String query, Pageable pageable);
}
