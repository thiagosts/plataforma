package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.CandidatesService;
import br.com.connekt.plataforma.domain.Candidates;
import br.com.connekt.plataforma.repository.CandidatesRepository;
import br.com.connekt.plataforma.repository.search.CandidatesSearchRepository;
import br.com.connekt.plataforma.service.dto.CandidatesDTO;
import br.com.connekt.plataforma.service.mapper.CandidatesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Candidates.
 */
@Service
@Transactional
public class CandidatesServiceImpl implements CandidatesService {

    private final Logger log = LoggerFactory.getLogger(CandidatesServiceImpl.class);

    private final CandidatesRepository candidatesRepository;

    private final CandidatesMapper candidatesMapper;

    private final CandidatesSearchRepository candidatesSearchRepository;

    public CandidatesServiceImpl(CandidatesRepository candidatesRepository, CandidatesMapper candidatesMapper, CandidatesSearchRepository candidatesSearchRepository) {
        this.candidatesRepository = candidatesRepository;
        this.candidatesMapper = candidatesMapper;
        this.candidatesSearchRepository = candidatesSearchRepository;
    }

    /**
     * Save a candidates.
     *
     * @param candidatesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CandidatesDTO save(CandidatesDTO candidatesDTO) {
        log.debug("Request to save Candidates : {}", candidatesDTO);

        Candidates candidates = candidatesMapper.toEntity(candidatesDTO);
        candidates = candidatesRepository.save(candidates);
        CandidatesDTO result = candidatesMapper.toDto(candidates);
        candidatesSearchRepository.save(candidates);
        return result;
    }

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidatesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidatesRepository.findAll(pageable)
            .map(candidatesMapper::toDto);
    }


    /**
     * Get one candidates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CandidatesDTO> findOne(Long id) {
        log.debug("Request to get Candidates : {}", id);
        return candidatesRepository.findById(id)
            .map(candidatesMapper::toDto);
    }

    /**
     * Delete the candidates by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidates : {}", id);
        candidatesRepository.deleteById(id);
        candidatesSearchRepository.deleteById(id);
    }

    /**
     * Search for the candidates corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidatesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Candidates for query {}", query);
        return candidatesSearchRepository.search(queryStringQuery(query), pageable)
            .map(candidatesMapper::toDto);
    }
}
