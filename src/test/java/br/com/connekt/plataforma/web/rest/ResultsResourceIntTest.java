package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Results;
import br.com.connekt.plataforma.repository.ResultsRepository;
import br.com.connekt.plataforma.repository.search.ResultsSearchRepository;
import br.com.connekt.plataforma.service.ResultsService;
import br.com.connekt.plataforma.service.dto.ResultsDTO;
import br.com.connekt.plataforma.service.mapper.ResultsMapper;
import br.com.connekt.plataforma.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static br.com.connekt.plataforma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResultsResource REST controller.
 *
 * @see ResultsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class ResultsResourceIntTest {

    private static final Float DEFAULT_VALUE = 1F;
    private static final Float UPDATED_VALUE = 2F;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FINAL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINAL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MAX_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MAX_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResultsRepository resultsRepository;

    @Autowired
    private ResultsMapper resultsMapper;

    @Autowired
    private ResultsService resultsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.ResultsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResultsSearchRepository mockResultsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResultsMockMvc;

    private Results results;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultsResource resultsResource = new ResultsResource(resultsService);
        this.restResultsMockMvc = MockMvcBuilders.standaloneSetup(resultsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Results createEntity(EntityManager em) {
        Results results = new Results()
            .value(DEFAULT_VALUE)
            .startTime(DEFAULT_START_TIME)
            .finalTime(DEFAULT_FINAL_TIME)
            .maxTime(DEFAULT_MAX_TIME);
        return results;
    }

    @Before
    public void initTest() {
        results = createEntity(em);
    }

    @Test
    @Transactional
    public void createResults() throws Exception {
        int databaseSizeBeforeCreate = resultsRepository.findAll().size();

        // Create the Results
        ResultsDTO resultsDTO = resultsMapper.toDto(results);
        restResultsMockMvc.perform(post("/api/results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDTO)))
            .andExpect(status().isCreated());

        // Validate the Results in the database
        List<Results> resultsList = resultsRepository.findAll();
        assertThat(resultsList).hasSize(databaseSizeBeforeCreate + 1);
        Results testResults = resultsList.get(resultsList.size() - 1);
        assertThat(testResults.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testResults.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testResults.getFinalTime()).isEqualTo(DEFAULT_FINAL_TIME);
        assertThat(testResults.getMaxTime()).isEqualTo(DEFAULT_MAX_TIME);

        // Validate the Results in Elasticsearch
        verify(mockResultsSearchRepository, times(1)).save(testResults);
    }

    @Test
    @Transactional
    public void createResultsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultsRepository.findAll().size();

        // Create the Results with an existing ID
        results.setId(1L);
        ResultsDTO resultsDTO = resultsMapper.toDto(results);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultsMockMvc.perform(post("/api/results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Results in the database
        List<Results> resultsList = resultsRepository.findAll();
        assertThat(resultsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Results in Elasticsearch
        verify(mockResultsSearchRepository, times(0)).save(results);
    }

    @Test
    @Transactional
    public void getAllResults() throws Exception {
        // Initialize the database
        resultsRepository.saveAndFlush(results);

        // Get all the resultsList
        restResultsMockMvc.perform(get("/api/results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(results.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].finalTime").value(hasItem(DEFAULT_FINAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].maxTime").value(hasItem(DEFAULT_MAX_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getResults() throws Exception {
        // Initialize the database
        resultsRepository.saveAndFlush(results);

        // Get the results
        restResultsMockMvc.perform(get("/api/results/{id}", results.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(results.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.finalTime").value(DEFAULT_FINAL_TIME.toString()))
            .andExpect(jsonPath("$.maxTime").value(DEFAULT_MAX_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResults() throws Exception {
        // Get the results
        restResultsMockMvc.perform(get("/api/results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResults() throws Exception {
        // Initialize the database
        resultsRepository.saveAndFlush(results);

        int databaseSizeBeforeUpdate = resultsRepository.findAll().size();

        // Update the results
        Results updatedResults = resultsRepository.findById(results.getId()).get();
        // Disconnect from session so that the updates on updatedResults are not directly saved in db
        em.detach(updatedResults);
        updatedResults
            .value(UPDATED_VALUE)
            .startTime(UPDATED_START_TIME)
            .finalTime(UPDATED_FINAL_TIME)
            .maxTime(UPDATED_MAX_TIME);
        ResultsDTO resultsDTO = resultsMapper.toDto(updatedResults);

        restResultsMockMvc.perform(put("/api/results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDTO)))
            .andExpect(status().isOk());

        // Validate the Results in the database
        List<Results> resultsList = resultsRepository.findAll();
        assertThat(resultsList).hasSize(databaseSizeBeforeUpdate);
        Results testResults = resultsList.get(resultsList.size() - 1);
        assertThat(testResults.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testResults.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testResults.getFinalTime()).isEqualTo(UPDATED_FINAL_TIME);
        assertThat(testResults.getMaxTime()).isEqualTo(UPDATED_MAX_TIME);

        // Validate the Results in Elasticsearch
        verify(mockResultsSearchRepository, times(1)).save(testResults);
    }

    @Test
    @Transactional
    public void updateNonExistingResults() throws Exception {
        int databaseSizeBeforeUpdate = resultsRepository.findAll().size();

        // Create the Results
        ResultsDTO resultsDTO = resultsMapper.toDto(results);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultsMockMvc.perform(put("/api/results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Results in the database
        List<Results> resultsList = resultsRepository.findAll();
        assertThat(resultsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Results in Elasticsearch
        verify(mockResultsSearchRepository, times(0)).save(results);
    }

    @Test
    @Transactional
    public void deleteResults() throws Exception {
        // Initialize the database
        resultsRepository.saveAndFlush(results);

        int databaseSizeBeforeDelete = resultsRepository.findAll().size();

        // Get the results
        restResultsMockMvc.perform(delete("/api/results/{id}", results.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Results> resultsList = resultsRepository.findAll();
        assertThat(resultsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Results in Elasticsearch
        verify(mockResultsSearchRepository, times(1)).deleteById(results.getId());
    }

    @Test
    @Transactional
    public void searchResults() throws Exception {
        // Initialize the database
        resultsRepository.saveAndFlush(results);
        when(mockResultsSearchRepository.search(queryStringQuery("id:" + results.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(results), PageRequest.of(0, 1), 1));
        // Search the results
        restResultsMockMvc.perform(get("/api/_search/results?query=id:" + results.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(results.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].finalTime").value(hasItem(DEFAULT_FINAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].maxTime").value(hasItem(DEFAULT_MAX_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Results.class);
        Results results1 = new Results();
        results1.setId(1L);
        Results results2 = new Results();
        results2.setId(results1.getId());
        assertThat(results1).isEqualTo(results2);
        results2.setId(2L);
        assertThat(results1).isNotEqualTo(results2);
        results1.setId(null);
        assertThat(results1).isNotEqualTo(results2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsDTO.class);
        ResultsDTO resultsDTO1 = new ResultsDTO();
        resultsDTO1.setId(1L);
        ResultsDTO resultsDTO2 = new ResultsDTO();
        assertThat(resultsDTO1).isNotEqualTo(resultsDTO2);
        resultsDTO2.setId(resultsDTO1.getId());
        assertThat(resultsDTO1).isEqualTo(resultsDTO2);
        resultsDTO2.setId(2L);
        assertThat(resultsDTO1).isNotEqualTo(resultsDTO2);
        resultsDTO1.setId(null);
        assertThat(resultsDTO1).isNotEqualTo(resultsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resultsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resultsMapper.fromId(null)).isNull();
    }
}
