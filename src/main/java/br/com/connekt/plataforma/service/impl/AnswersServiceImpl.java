package br.com.connekt.plataforma.service.impl;

import br.com.connekt.plataforma.service.AnswersService;
import br.com.connekt.plataforma.domain.Answers;
import br.com.connekt.plataforma.repository.AnswersRepository;
import br.com.connekt.plataforma.repository.search.AnswersSearchRepository;
import br.com.connekt.plataforma.service.dto.AnswersDTO;
import br.com.connekt.plataforma.service.mapper.AnswersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Answers.
 */
@Service
@Transactional
public class AnswersServiceImpl implements AnswersService {

    private final Logger log = LoggerFactory.getLogger(AnswersServiceImpl.class);

    private final AnswersRepository answersRepository;

    private final AnswersMapper answersMapper;

    private final AnswersSearchRepository answersSearchRepository;

    public AnswersServiceImpl(AnswersRepository answersRepository, AnswersMapper answersMapper, AnswersSearchRepository answersSearchRepository) {
        this.answersRepository = answersRepository;
        this.answersMapper = answersMapper;
        this.answersSearchRepository = answersSearchRepository;
    }

    /**
     * Save a answers.
     *
     * @param answersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AnswersDTO save(AnswersDTO answersDTO) {
        log.debug("Request to save Answers : {}", answersDTO);

        Answers answers = answersMapper.toEntity(answersDTO);
        answers = answersRepository.save(answers);
        AnswersDTO result = answersMapper.toDto(answers);
        answersSearchRepository.save(answers);
        return result;
    }

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Answers");
        return answersRepository.findAll(pageable)
            .map(answersMapper::toDto);
    }


    /**
     * Get one answers by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnswersDTO> findOne(Long id) {
        log.debug("Request to get Answers : {}", id);
        return answersRepository.findById(id)
            .map(answersMapper::toDto);
    }

    /**
     * Delete the answers by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answers : {}", id);
        answersRepository.deleteById(id);
        answersSearchRepository.deleteById(id);
    }

    /**
     * Search for the answers corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnswersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Answers for query {}", query);
        return answersSearchRepository.search(queryStringQuery(query), pageable)
            .map(answersMapper::toDto);
    }
}
