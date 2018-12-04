package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.CandidatesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Candidates.
 */
public interface CandidatesService {

    /**
     * Save a candidates.
     *
     * @param candidatesDTO the entity to save
     * @return the persisted entity
     */
    CandidatesDTO save(CandidatesDTO candidatesDTO);

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CandidatesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" candidates.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CandidatesDTO> findOne(Long id);

    /**
     * Delete the "id" candidates.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the candidates corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CandidatesDTO> search(String query, Pageable pageable);
}
