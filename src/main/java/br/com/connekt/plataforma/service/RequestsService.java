package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.RequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Requests.
 */
public interface RequestsService {

    /**
     * Save a requests.
     *
     * @param requestsDTO the entity to save
     * @return the persisted entity
     */
    RequestsDTO save(RequestsDTO requestsDTO);

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" requests.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RequestsDTO> findOne(Long id);

    /**
     * Delete the "id" requests.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the requests corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RequestsDTO> search(String query, Pageable pageable);
}
