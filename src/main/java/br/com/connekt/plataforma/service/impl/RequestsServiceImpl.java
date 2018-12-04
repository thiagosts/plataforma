package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.RequestsService;
import br.com.connekt.plataforma.domain.Requests;
import br.com.connekt.plataforma.repository.RequestsRepository;
import br.com.connekt.plataforma.repository.search.RequestsSearchRepository;
import br.com.connekt.plataforma.service.dto.RequestsDTO;
import br.com.connekt.plataforma.service.mapper.RequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Requests.
 */
@Service
@Transactional
public class RequestsServiceImpl implements RequestsService {

    private final Logger log = LoggerFactory.getLogger(RequestsServiceImpl.class);

    private final RequestsRepository requestsRepository;

    private final RequestsMapper requestsMapper;

    private final RequestsSearchRepository requestsSearchRepository;

    public RequestsServiceImpl(RequestsRepository requestsRepository, RequestsMapper requestsMapper, RequestsSearchRepository requestsSearchRepository) {
        this.requestsRepository = requestsRepository;
        this.requestsMapper = requestsMapper;
        this.requestsSearchRepository = requestsSearchRepository;
    }

    /**
     * Save a requests.
     *
     * @param requestsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestsDTO save(RequestsDTO requestsDTO) {
        log.debug("Request to save Requests : {}", requestsDTO);

        Requests requests = requestsMapper.toEntity(requestsDTO);
        requests = requestsRepository.save(requests);
        RequestsDTO result = requestsMapper.toDto(requests);
        requestsSearchRepository.save(requests);
        return result;
    }

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestsRepository.findAll(pageable)
            .map(requestsMapper::toDto);
    }


    /**
     * Get one requests by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RequestsDTO> findOne(Long id) {
        log.debug("Request to get Requests : {}", id);
        return requestsRepository.findById(id)
            .map(requestsMapper::toDto);
    }

    /**
     * Delete the requests by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requests : {}", id);
        requestsRepository.deleteById(id);
        requestsSearchRepository.deleteById(id);
    }

    /**
     * Search for the requests corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RequestsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requests for query {}", query);
        return requestsSearchRepository.search(queryStringQuery(query), pageable)
            .map(requestsMapper::toDto);
    }
}
