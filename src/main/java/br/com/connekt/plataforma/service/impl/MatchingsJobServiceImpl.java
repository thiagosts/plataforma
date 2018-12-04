package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.MatchingsJobService;
import br.com.connekt.plataforma.domain.MatchingsJob;
import br.com.connekt.plataforma.repository.MatchingsJobRepository;
import br.com.connekt.plataforma.repository.search.MatchingsJobSearchRepository;
import br.com.connekt.plataforma.service.dto.MatchingsJobDTO;
import br.com.connekt.plataforma.service.mapper.MatchingsJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MatchingsJob.
 */
@Service
@Transactional
public class MatchingsJobServiceImpl implements MatchingsJobService {

    private final Logger log = LoggerFactory.getLogger(MatchingsJobServiceImpl.class);

    private final MatchingsJobRepository matchingsJobRepository;

    private final MatchingsJobMapper matchingsJobMapper;

    private final MatchingsJobSearchRepository matchingsJobSearchRepository;

    public MatchingsJobServiceImpl(MatchingsJobRepository matchingsJobRepository, MatchingsJobMapper matchingsJobMapper, MatchingsJobSearchRepository matchingsJobSearchRepository) {
        this.matchingsJobRepository = matchingsJobRepository;
        this.matchingsJobMapper = matchingsJobMapper;
        this.matchingsJobSearchRepository = matchingsJobSearchRepository;
    }

    /**
     * Save a matchingsJob.
     *
     * @param matchingsJobDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MatchingsJobDTO save(MatchingsJobDTO matchingsJobDTO) {
        log.debug("Request to save MatchingsJob : {}", matchingsJobDTO);

        MatchingsJob matchingsJob = matchingsJobMapper.toEntity(matchingsJobDTO);
        matchingsJob = matchingsJobRepository.save(matchingsJob);
        MatchingsJobDTO result = matchingsJobMapper.toDto(matchingsJob);
        matchingsJobSearchRepository.save(matchingsJob);
        return result;
    }

    /**
     * Get all the matchingsJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchingsJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchingsJobs");
        return matchingsJobRepository.findAll(pageable)
            .map(matchingsJobMapper::toDto);
    }


    /**
     * Get one matchingsJob by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MatchingsJobDTO> findOne(Long id) {
        log.debug("Request to get MatchingsJob : {}", id);
        return matchingsJobRepository.findById(id)
            .map(matchingsJobMapper::toDto);
    }

    /**
     * Delete the matchingsJob by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MatchingsJob : {}", id);
        matchingsJobRepository.deleteById(id);
        matchingsJobSearchRepository.deleteById(id);
    }

    /**
     * Search for the matchingsJob corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatchingsJobDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MatchingsJobs for query {}", query);
        return matchingsJobSearchRepository.search(queryStringQuery(query), pageable)
            .map(matchingsJobMapper::toDto);
    }
}
