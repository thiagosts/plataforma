package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.ResourcesService;
import br.com.connekt.plataforma.domain.Resources;
import br.com.connekt.plataforma.repository.ResourcesRepository;
import br.com.connekt.plataforma.repository.search.ResourcesSearchRepository;
import br.com.connekt.plataforma.service.dto.ResourcesDTO;
import br.com.connekt.plataforma.service.mapper.ResourcesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Resources.
 */
@Service
@Transactional
public class ResourcesServiceImpl implements ResourcesService {

    private final Logger log = LoggerFactory.getLogger(ResourcesServiceImpl.class);

    private final ResourcesRepository resourcesRepository;

    private final ResourcesMapper resourcesMapper;

    private final ResourcesSearchRepository resourcesSearchRepository;

    public ResourcesServiceImpl(ResourcesRepository resourcesRepository, ResourcesMapper resourcesMapper, ResourcesSearchRepository resourcesSearchRepository) {
        this.resourcesRepository = resourcesRepository;
        this.resourcesMapper = resourcesMapper;
        this.resourcesSearchRepository = resourcesSearchRepository;
    }

    /**
     * Save a resources.
     *
     * @param resourcesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResourcesDTO save(ResourcesDTO resourcesDTO) {
        log.debug("Request to save Resources : {}", resourcesDTO);

        Resources resources = resourcesMapper.toEntity(resourcesDTO);
        resources = resourcesRepository.save(resources);
        ResourcesDTO result = resourcesMapper.toDto(resources);
        resourcesSearchRepository.save(resources);
        return result;
    }

    /**
     * Get all the resources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        return resourcesRepository.findAll(pageable)
            .map(resourcesMapper::toDto);
    }


    /**
     * Get one resources by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResourcesDTO> findOne(Long id) {
        log.debug("Request to get Resources : {}", id);
        return resourcesRepository.findById(id)
            .map(resourcesMapper::toDto);
    }

    /**
     * Delete the resources by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resources : {}", id);
        resourcesRepository.deleteById(id);
        resourcesSearchRepository.deleteById(id);
    }

    /**
     * Search for the resources corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourcesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Resources for query {}", query);
        return resourcesSearchRepository.search(queryStringQuery(query), pageable)
            .map(resourcesMapper::toDto);
    }
}
