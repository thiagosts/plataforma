package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.PortalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Portal.
 */
public interface PortalService {

    /**
     * Save a portal.
     *
     * @param portalDTO the entity to save
     * @return the persisted entity
     */
    PortalDTO save(PortalDTO portalDTO);

    /**
     * Get all the portals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PortalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" portal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PortalDTO> findOne(Long id);

    /**
     * Delete the "id" portal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the portal corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PortalDTO> search(String query, Pageable pageable);
}
