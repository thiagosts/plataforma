package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.ResultsDetailsService;
import br.com.connekt.plataforma.domain.ResultsDetails;
import br.com.connekt.plataforma.repository.ResultsDetailsRepository;
import br.com.connekt.plataforma.repository.search.ResultsDetailsSearchRepository;
import br.com.connekt.plataforma.service.dto.ResultsDetailsDTO;
import br.com.connekt.plataforma.service.mapper.ResultsDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ResultsDetails.
 */
@Service
@Transactional
public class ResultsDetailsServiceImpl implements ResultsDetailsService {

    private final Logger log = LoggerFactory.getLogger(ResultsDetailsServiceImpl.class);

    private final ResultsDetailsRepository resultsDetailsRepository;

    private final ResultsDetailsMapper resultsDetailsMapper;

    private final ResultsDetailsSearchRepository resultsDetailsSearchRepository;

    public ResultsDetailsServiceImpl(ResultsDetailsRepository resultsDetailsRepository, ResultsDetailsMapper resultsDetailsMapper, ResultsDetailsSearchRepository resultsDetailsSearchRepository) {
        this.resultsDetailsRepository = resultsDetailsRepository;
        this.resultsDetailsMapper = resultsDetailsMapper;
        this.resultsDetailsSearchRepository = resultsDetailsSearchRepository;
    }

    /**
     * Save a resultsDetails.
     *
     * @param resultsDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResultsDetailsDTO save(ResultsDetailsDTO resultsDetailsDTO) {
        log.debug("Request to save ResultsDetails : {}", resultsDetailsDTO);

        ResultsDetails resultsDetails = resultsDetailsMapper.toEntity(resultsDetailsDTO);
        resultsDetails = resultsDetailsRepository.save(resultsDetails);
        ResultsDetailsDTO result = resultsDetailsMapper.toDto(resultsDetails);
        resultsDetailsSearchRepository.save(resultsDetails);
        return result;
    }

    /**
     * Get all the resultsDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResultsDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResultsDetails");
        return resultsDetailsRepository.findAll(pageable)
            .map(resultsDetailsMapper::toDto);
    }


    /**
     * Get one resultsDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResultsDetailsDTO> findOne(Long id) {
        log.debug("Request to get ResultsDetails : {}", id);
        return resultsDetailsRepository.findById(id)
            .map(resultsDetailsMapper::toDto);
    }

    /**
     * Delete the resultsDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultsDetails : {}", id);
        resultsDetailsRepository.deleteById(id);
        resultsDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the resultsDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResultsDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ResultsDetails for query {}", query);
        return resultsDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(resultsDetailsMapper::toDto);
    }
}
