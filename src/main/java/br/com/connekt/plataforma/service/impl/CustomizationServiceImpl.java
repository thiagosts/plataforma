package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.CustomizationService;
import br.com.connekt.plataforma.domain.Customization;
import br.com.connekt.plataforma.repository.CustomizationRepository;
import br.com.connekt.plataforma.repository.search.CustomizationSearchRepository;
import br.com.connekt.plataforma.service.dto.CustomizationDTO;
import br.com.connekt.plataforma.service.mapper.CustomizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Customization.
 */
@Service
@Transactional
public class CustomizationServiceImpl implements CustomizationService {

    private final Logger log = LoggerFactory.getLogger(CustomizationServiceImpl.class);

    private final CustomizationRepository customizationRepository;

    private final CustomizationMapper customizationMapper;

    private final CustomizationSearchRepository customizationSearchRepository;

    public CustomizationServiceImpl(CustomizationRepository customizationRepository, CustomizationMapper customizationMapper, CustomizationSearchRepository customizationSearchRepository) {
        this.customizationRepository = customizationRepository;
        this.customizationMapper = customizationMapper;
        this.customizationSearchRepository = customizationSearchRepository;
    }

    /**
     * Save a customization.
     *
     * @param customizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomizationDTO save(CustomizationDTO customizationDTO) {
        log.debug("Request to save Customization : {}", customizationDTO);

        Customization customization = customizationMapper.toEntity(customizationDTO);
        customization = customizationRepository.save(customization);
        CustomizationDTO result = customizationMapper.toDto(customization);
        customizationSearchRepository.save(customization);
        return result;
    }

    /**
     * Get all the customizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customizations");
        return customizationRepository.findAll(pageable)
            .map(customizationMapper::toDto);
    }


    /**
     * Get one customization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomizationDTO> findOne(Long id) {
        log.debug("Request to get Customization : {}", id);
        return customizationRepository.findById(id)
            .map(customizationMapper::toDto);
    }

    /**
     * Delete the customization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Customization : {}", id);
        customizationRepository.deleteById(id);
        customizationSearchRepository.deleteById(id);
    }

    /**
     * Search for the customization corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Customizations for query {}", query);
        return customizationSearchRepository.search(queryStringQuery(query), pageable)
            .map(customizationMapper::toDto);
    }
}
