package br.com.connekt.plataforma.service;

import br.com.connekt.plataforma.service.dto.BenefitsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Benefits.
 */
public interface BenefitsService {

    /**
     * Save a benefits.
     *
     * @param benefitsDTO the entity to save
     * @return the persisted entity
     */
    BenefitsDTO save(BenefitsDTO benefitsDTO);

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BenefitsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" benefits.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BenefitsDTO> findOne(Long id);

    /**
     * Delete the "id" benefits.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the benefits corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BenefitsDTO> search(String query, Pageable pageable);
}
