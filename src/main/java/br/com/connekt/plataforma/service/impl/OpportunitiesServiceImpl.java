package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.OpportunitiesService;
import br.com.connekt.plataforma.domain.Opportunities;
import br.com.connekt.plataforma.repository.OpportunitiesRepository;
import br.com.connekt.plataforma.repository.search.OpportunitiesSearchRepository;
import br.com.connekt.plataforma.service.dto.OpportunitiesDTO;
import br.com.connekt.plataforma.service.mapper.OpportunitiesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Opportunities.
 */
@Service
@Transactional
public class OpportunitiesServiceImpl implements OpportunitiesService {

    private final Logger log = LoggerFactory.getLogger(OpportunitiesServiceImpl.class);

    private final OpportunitiesRepository opportunitiesRepository;

    private final OpportunitiesMapper opportunitiesMapper;

    private final OpportunitiesSearchRepository opportunitiesSearchRepository;

    public OpportunitiesServiceImpl(OpportunitiesRepository opportunitiesRepository, OpportunitiesMapper opportunitiesMapper, OpportunitiesSearchRepository opportunitiesSearchRepository) {
        this.opportunitiesRepository = opportunitiesRepository;
        this.opportunitiesMapper = opportunitiesMapper;
        this.opportunitiesSearchRepository = opportunitiesSearchRepository;
    }

    /**
     * Save a opportunities.
     *
     * @param opportunitiesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OpportunitiesDTO save(OpportunitiesDTO opportunitiesDTO) {
        log.debug("Request to save Opportunities : {}", opportunitiesDTO);

        Opportunities opportunities = opportunitiesMapper.toEntity(opportunitiesDTO);
        opportunities = opportunitiesRepository.save(opportunities);
        OpportunitiesDTO result = opportunitiesMapper.toDto(opportunities);
        opportunitiesSearchRepository.save(opportunities);
        return result;
    }

    /**
     * Get all the opportunities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OpportunitiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Opportunities");
        return opportunitiesRepository.findAll(pageable)
            .map(opportunitiesMapper::toDto);
    }


    /**
     * Get one opportunities by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OpportunitiesDTO> findOne(Long id) {
        log.debug("Request to get Opportunities : {}", id);
        return opportunitiesRepository.findById(id)
            .map(opportunitiesMapper::toDto);
    }

    /**
     * Delete the opportunities by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Opportunities : {}", id);
        opportunitiesRepository.deleteById(id);
        opportunitiesSearchRepository.deleteById(id);
    }

    /**
     * Search for the opportunities corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OpportunitiesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Opportunities for query {}", query);
        return opportunitiesSearchRepository.search(queryStringQuery(query), pageable)
            .map(opportunitiesMapper::toDto);
    }
}
