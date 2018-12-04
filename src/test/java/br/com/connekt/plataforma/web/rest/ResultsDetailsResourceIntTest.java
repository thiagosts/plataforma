package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.ResultsDetails;
import br.com.connekt.plataforma.repository.ResultsDetailsRepository;
import br.com.connekt.plataforma.repository.search.ResultsDetailsSearchRepository;
import br.com.connekt.plataforma.service.ResultsDetailsService;
import br.com.connekt.plataforma.service.dto.ResultsDetailsDTO;
import br.com.connekt.plataforma.service.mapper.ResultsDetailsMapper;
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
import java.math.BigDecimal;
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
 * Test class for the ResultsDetailsResource REST controller.
 *
 * @see ResultsDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class ResultsDetailsResourceIntTest {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResultsDetailsRepository resultsDetailsRepository;

    @Autowired
    private ResultsDetailsMapper resultsDetailsMapper;

    @Autowired
    private ResultsDetailsService resultsDetailsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.ResultsDetailsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResultsDetailsSearchRepository mockResultsDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResultsDetailsMockMvc;

    private ResultsDetails resultsDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultsDetailsResource resultsDetailsResource = new ResultsDetailsResource(resultsDetailsService);
        this.restResultsDetailsMockMvc = MockMvcBuilders.standaloneSetup(resultsDetailsResource)
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
    public static ResultsDetails createEntity(EntityManager em) {
        ResultsDetails resultsDetails = new ResultsDetails()
            .value(DEFAULT_VALUE)
            .result(DEFAULT_RESULT)
            .createdDate(DEFAULT_CREATED_DATE);
        return resultsDetails;
    }

    @Before
    public void initTest() {
        resultsDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultsDetails() throws Exception {
        int databaseSizeBeforeCreate = resultsDetailsRepository.findAll().size();

        // Create the ResultsDetails
        ResultsDetailsDTO resultsDetailsDTO = resultsDetailsMapper.toDto(resultsDetails);
        restResultsDetailsMockMvc.perform(post("/api/results-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ResultsDetails in the database
        List<ResultsDetails> resultsDetailsList = resultsDetailsRepository.findAll();
        assertThat(resultsDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ResultsDetails testResultsDetails = resultsDetailsList.get(resultsDetailsList.size() - 1);
        assertThat(testResultsDetails.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testResultsDetails.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testResultsDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);

        // Validate the ResultsDetails in Elasticsearch
        verify(mockResultsDetailsSearchRepository, times(1)).save(testResultsDetails);
    }

    @Test
    @Transactional
    public void createResultsDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultsDetailsRepository.findAll().size();

        // Create the ResultsDetails with an existing ID
        resultsDetails.setId(1L);
        ResultsDetailsDTO resultsDetailsDTO = resultsDetailsMapper.toDto(resultsDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultsDetailsMockMvc.perform(post("/api/results-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultsDetails in the database
        List<ResultsDetails> resultsDetailsList = resultsDetailsRepository.findAll();
        assertThat(resultsDetailsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ResultsDetails in Elasticsearch
        verify(mockResultsDetailsSearchRepository, times(0)).save(resultsDetails);
    }

    @Test
    @Transactional
    public void getAllResultsDetails() throws Exception {
        // Initialize the database
        resultsDetailsRepository.saveAndFlush(resultsDetails);

        // Get all the resultsDetailsList
        restResultsDetailsMockMvc.perform(get("/api/results-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getResultsDetails() throws Exception {
        // Initialize the database
        resultsDetailsRepository.saveAndFlush(resultsDetails);

        // Get the resultsDetails
        restResultsDetailsMockMvc.perform(get("/api/results-details/{id}", resultsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultsDetails.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultsDetails() throws Exception {
        // Get the resultsDetails
        restResultsDetailsMockMvc.perform(get("/api/results-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultsDetails() throws Exception {
        // Initialize the database
        resultsDetailsRepository.saveAndFlush(resultsDetails);

        int databaseSizeBeforeUpdate = resultsDetailsRepository.findAll().size();

        // Update the resultsDetails
        ResultsDetails updatedResultsDetails = resultsDetailsRepository.findById(resultsDetails.getId()).get();
        // Disconnect from session so that the updates on updatedResultsDetails are not directly saved in db
        em.detach(updatedResultsDetails);
        updatedResultsDetails
            .value(UPDATED_VALUE)
            .result(UPDATED_RESULT)
            .createdDate(UPDATED_CREATED_DATE);
        ResultsDetailsDTO resultsDetailsDTO = resultsDetailsMapper.toDto(updatedResultsDetails);

        restResultsDetailsMockMvc.perform(put("/api/results-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ResultsDetails in the database
        List<ResultsDetails> resultsDetailsList = resultsDetailsRepository.findAll();
        assertThat(resultsDetailsList).hasSize(databaseSizeBeforeUpdate);
        ResultsDetails testResultsDetails = resultsDetailsList.get(resultsDetailsList.size() - 1);
        assertThat(testResultsDetails.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testResultsDetails.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testResultsDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);

        // Validate the ResultsDetails in Elasticsearch
        verify(mockResultsDetailsSearchRepository, times(1)).save(testResultsDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingResultsDetails() throws Exception {
        int databaseSizeBeforeUpdate = resultsDetailsRepository.findAll().size();

        // Create the ResultsDetails
        ResultsDetailsDTO resultsDetailsDTO = resultsDetailsMapper.toDto(resultsDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultsDetailsMockMvc.perform(put("/api/results-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultsDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultsDetails in the database
        List<ResultsDetails> resultsDetailsList = resultsDetailsRepository.findAll();
        assertThat(resultsDetailsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ResultsDetails in Elasticsearch
        verify(mockResultsDetailsSearchRepository, times(0)).save(resultsDetails);
    }

    @Test
    @Transactional
    public void deleteResultsDetails() throws Exception {
        // Initialize the database
        resultsDetailsRepository.saveAndFlush(resultsDetails);

        int databaseSizeBeforeDelete = resultsDetailsRepository.findAll().size();

        // Get the resultsDetails
        restResultsDetailsMockMvc.perform(delete("/api/results-details/{id}", resultsDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultsDetails> resultsDetailsList = resultsDetailsRepository.findAll();
        assertThat(resultsDetailsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ResultsDetails in Elasticsearch
        verify(mockResultsDetailsSearchRepository, times(1)).deleteById(resultsDetails.getId());
    }

    @Test
    @Transactional
    public void searchResultsDetails() throws Exception {
        // Initialize the database
        resultsDetailsRepository.saveAndFlush(resultsDetails);
        when(mockResultsDetailsSearchRepository.search(queryStringQuery("id:" + resultsDetails.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(resultsDetails), PageRequest.of(0, 1), 1));
        // Search the resultsDetails
        restResultsDetailsMockMvc.perform(get("/api/_search/results-details?query=id:" + resultsDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultsDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsDetails.class);
        ResultsDetails resultsDetails1 = new ResultsDetails();
        resultsDetails1.setId(1L);
        ResultsDetails resultsDetails2 = new ResultsDetails();
        resultsDetails2.setId(resultsDetails1.getId());
        assertThat(resultsDetails1).isEqualTo(resultsDetails2);
        resultsDetails2.setId(2L);
        assertThat(resultsDetails1).isNotEqualTo(resultsDetails2);
        resultsDetails1.setId(null);
        assertThat(resultsDetails1).isNotEqualTo(resultsDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultsDetailsDTO.class);
        ResultsDetailsDTO resultsDetailsDTO1 = new ResultsDetailsDTO();
        resultsDetailsDTO1.setId(1L);
        ResultsDetailsDTO resultsDetailsDTO2 = new ResultsDetailsDTO();
        assertThat(resultsDetailsDTO1).isNotEqualTo(resultsDetailsDTO2);
        resultsDetailsDTO2.setId(resultsDetailsDTO1.getId());
        assertThat(resultsDetailsDTO1).isEqualTo(resultsDetailsDTO2);
        resultsDetailsDTO2.setId(2L);
        assertThat(resultsDetailsDTO1).isNotEqualTo(resultsDetailsDTO2);
        resultsDetailsDTO1.setId(null);
        assertThat(resultsDetailsDTO1).isNotEqualTo(resultsDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resultsDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resultsDetailsMapper.fromId(null)).isNull();
    }
}
