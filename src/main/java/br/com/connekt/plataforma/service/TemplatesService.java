package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.TemplatesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Templates.
 */
public interface TemplatesService {

    /**
     * Save a templates.
     *
     * @param templatesDTO the entity to save
     * @return the persisted entity
     */
    TemplatesDTO save(TemplatesDTO templatesDTO);

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TemplatesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" templates.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TemplatesDTO> findOne(Long id);

    /**
     * Delete the "id" templates.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the templates corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TemplatesDTO> search(String query, Pageable pageable);
}
