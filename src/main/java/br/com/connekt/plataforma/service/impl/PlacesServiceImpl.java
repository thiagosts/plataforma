package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.PlacesService;
import br.com.connekt.plataforma.domain.Places;
import br.com.connekt.plataforma.repository.PlacesRepository;
import br.com.connekt.plataforma.repository.search.PlacesSearchRepository;
import br.com.connekt.plataforma.service.dto.PlacesDTO;
import br.com.connekt.plataforma.service.mapper.PlacesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Places.
 */
@Service
@Transactional
public class PlacesServiceImpl implements PlacesService {

    private final Logger log = LoggerFactory.getLogger(PlacesServiceImpl.class);

    private final PlacesRepository placesRepository;

    private final PlacesMapper placesMapper;

    private final PlacesSearchRepository placesSearchRepository;

    public PlacesServiceImpl(PlacesRepository placesRepository, PlacesMapper placesMapper, PlacesSearchRepository placesSearchRepository) {
        this.placesRepository = placesRepository;
        this.placesMapper = placesMapper;
        this.placesSearchRepository = placesSearchRepository;
    }

    /**
     * Save a places.
     *
     * @param placesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlacesDTO save(PlacesDTO placesDTO) {
        log.debug("Request to save Places : {}", placesDTO);

        Places places = placesMapper.toEntity(placesDTO);
        places = placesRepository.save(places);
        PlacesDTO result = placesMapper.toDto(places);
        placesSearchRepository.save(places);
        return result;
    }

    /**
     * Get all the places.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlacesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        return placesRepository.findAll(pageable)
            .map(placesMapper::toDto);
    }


    /**
     * Get one places by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlacesDTO> findOne(Long id) {
        log.debug("Request to get Places : {}", id);
        return placesRepository.findById(id)
            .map(placesMapper::toDto);
    }

    /**
     * Delete the places by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Places : {}", id);
        placesRepository.deleteById(id);
        placesSearchRepository.deleteById(id);
    }

    /**
     * Search for the places corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlacesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Places for query {}", query);
        return placesSearchRepository.search(queryStringQuery(query), pageable)
            .map(placesMapper::toDto);
    }
}
