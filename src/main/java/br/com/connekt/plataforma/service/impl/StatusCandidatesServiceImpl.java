package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.StatusCandidatesService;
import br.com.connekt.plataforma.domain.StatusCandidates;
import br.com.connekt.plataforma.repository.StatusCandidatesRepository;
import br.com.connekt.plataforma.repository.search.StatusCandidatesSearchRepository;
import br.com.connekt.plataforma.service.dto.StatusCandidatesDTO;
import br.com.connekt.plataforma.service.mapper.StatusCandidatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StatusCandidates.
 */
@Service
@Transactional
public class StatusCandidatesServiceImpl implements StatusCandidatesService {

    private final Logger log = LoggerFactory.getLogger(StatusCandidatesServiceImpl.class);

    private final StatusCandidatesRepository statusCandidatesRepository;

    private final StatusCandidatesMapper statusCandidatesMapper;

    private final StatusCandidatesSearchRepository statusCandidatesSearchRepository;

    public StatusCandidatesServiceImpl(StatusCandidatesRepository statusCandidatesRepository, StatusCandidatesMapper statusCandidatesMapper, StatusCandidatesSearchRepository statusCandidatesSearchRepository) {
        this.statusCandidatesRepository = statusCandidatesRepository;
        this.statusCandidatesMapper = statusCandidatesMapper;
        this.statusCandidatesSearchRepository = statusCandidatesSearchRepository;
    }

    /**
     * Save a statusCandidates.
     *
     * @param statusCandidatesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatusCandidatesDTO save(StatusCandidatesDTO statusCandidatesDTO) {
        log.debug("Request to save StatusCandidates : {}", statusCandidatesDTO);

        StatusCandidates statusCandidates = statusCandidatesMapper.toEntity(statusCandidatesDTO);
        statusCandidates = statusCandidatesRepository.save(statusCandidates);
        StatusCandidatesDTO result = statusCandidatesMapper.toDto(statusCandidates);
        statusCandidatesSearchRepository.save(statusCandidates);
        return result;
    }

    /**
     * Get all the statusCandidates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatusCandidatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusCandidates");
        return statusCandidatesRepository.findAll(pageable)
            .map(statusCandidatesMapper::toDto);
    }


    /**
     * Get one statusCandidates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StatusCandidatesDTO> findOne(Long id) {
        log.debug("Request to get StatusCandidates : {}", id);
        return statusCandidatesRepository.findById(id)
            .map(statusCandidatesMapper::toDto);
    }

    /**
     * Delete the statusCandidates by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusCandidates : {}", id);
        statusCandidatesRepository.deleteById(id);
        statusCandidatesSearchRepository.deleteById(id);
    }

    /**
     * Search for the statusCandidates corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StatusCandidatesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StatusCandidates for query {}", query);
        return statusCandidatesSearchRepository.search(queryStringQuery(query), pageable)
            .map(statusCandidatesMapper::toDto);
    }
}
