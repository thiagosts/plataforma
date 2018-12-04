package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.PortalService;
import br.com.connekt.plataforma.domain.Portal;
import br.com.connekt.plataforma.repository.PortalRepository;
import br.com.connekt.plataforma.repository.search.PortalSearchRepository;
import br.com.connekt.plataforma.service.dto.PortalDTO;
import br.com.connekt.plataforma.service.mapper.PortalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Portal.
 */
@Service
@Transactional
public class PortalServiceImpl implements PortalService {

    private final Logger log = LoggerFactory.getLogger(PortalServiceImpl.class);

    private final PortalRepository portalRepository;

    private final PortalMapper portalMapper;

    private final PortalSearchRepository portalSearchRepository;

    public PortalServiceImpl(PortalRepository portalRepository, PortalMapper portalMapper, PortalSearchRepository portalSearchRepository) {
        this.portalRepository = portalRepository;
        this.portalMapper = portalMapper;
        this.portalSearchRepository = portalSearchRepository;
    }

    /**
     * Save a portal.
     *
     * @param portalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PortalDTO save(PortalDTO portalDTO) {
        log.debug("Request to save Portal : {}", portalDTO);

        Portal portal = portalMapper.toEntity(portalDTO);
        portal = portalRepository.save(portal);
        PortalDTO result = portalMapper.toDto(portal);
        portalSearchRepository.save(portal);
        return result;
    }

    /**
     * Get all the portals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Portals");
        return portalRepository.findAll(pageable)
            .map(portalMapper::toDto);
    }


    /**
     * Get one portal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PortalDTO> findOne(Long id) {
        log.debug("Request to get Portal : {}", id);
        return portalRepository.findById(id)
            .map(portalMapper::toDto);
    }

    /**
     * Delete the portal by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Portal : {}", id);
        portalRepository.deleteById(id);
        portalSearchRepository.deleteById(id);
    }

    /**
     * Search for the portal corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PortalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Portals for query {}", query);
        return portalSearchRepository.search(queryStringQuery(query), pageable)
            .map(portalMapper::toDto);
    }
}
