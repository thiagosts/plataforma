package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.StatusCandidates;
import br.com.connekt.plataforma.repository.StatusCandidatesRepository;
import br.com.connekt.plataforma.repository.search.StatusCandidatesSearchRepository;
import br.com.connekt.plataforma.service.StatusCandidatesService;
import br.com.connekt.plataforma.service.dto.StatusCandidatesDTO;
import br.com.connekt.plataforma.service.mapper.StatusCandidatesMapper;
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
 * Test class for the StatusCandidatesResource REST controller.
 *
 * @see StatusCandidatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class StatusCandidatesResourceIntTest {

    private static final String DEFAULT_STAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_STAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUB_STAGE_NAME = "BBBBBBBBBB";

    @Autowired
    private StatusCandidatesRepository statusCandidatesRepository;

    @Autowired
    private StatusCandidatesMapper statusCandidatesMapper;

    @Autowired
    private StatusCandidatesService statusCandidatesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.StatusCandidatesSearchRepositoryMockConfiguration
     */
    @Autowired
    private StatusCandidatesSearchRepository mockStatusCandidatesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatusCandidatesMockMvc;

    private StatusCandidates statusCandidates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusCandidatesResource statusCandidatesResource = new StatusCandidatesResource(statusCandidatesService);
        this.restStatusCandidatesMockMvc = MockMvcBuilders.standaloneSetup(statusCandidatesResource)
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
    public static StatusCandidates createEntity(EntityManager em) {
        StatusCandidates statusCandidates = new StatusCandidates()
            .stageName(DEFAULT_STAGE_NAME)
            .source(DEFAULT_SOURCE)
            .subStageName(DEFAULT_SUB_STAGE_NAME);
        return statusCandidates;
    }

    @Before
    public void initTest() {
        statusCandidates = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusCandidates() throws Exception {
        int databaseSizeBeforeCreate = statusCandidatesRepository.findAll().size();

        // Create the StatusCandidates
        StatusCandidatesDTO statusCandidatesDTO = statusCandidatesMapper.toDto(statusCandidates);
        restStatusCandidatesMockMvc.perform(post("/api/status-candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusCandidatesDTO)))
            .andExpect(status().isCreated());

        // Validate the StatusCandidates in the database
        List<StatusCandidates> statusCandidatesList = statusCandidatesRepository.findAll();
        assertThat(statusCandidatesList).hasSize(databaseSizeBeforeCreate + 1);
        StatusCandidates testStatusCandidates = statusCandidatesList.get(statusCandidatesList.size() - 1);
        assertThat(testStatusCandidates.getStageName()).isEqualTo(DEFAULT_STAGE_NAME);
        assertThat(testStatusCandidates.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testStatusCandidates.getSubStageName()).isEqualTo(DEFAULT_SUB_STAGE_NAME);

        // Validate the StatusCandidates in Elasticsearch
        verify(mockStatusCandidatesSearchRepository, times(1)).save(testStatusCandidates);
    }

    @Test
    @Transactional
    public void createStatusCandidatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusCandidatesRepository.findAll().size();

        // Create the StatusCandidates with an existing ID
        statusCandidates.setId(1L);
        StatusCandidatesDTO statusCandidatesDTO = statusCandidatesMapper.toDto(statusCandidates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusCandidatesMockMvc.perform(post("/api/status-candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusCandidatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StatusCandidates in the database
        List<StatusCandidates> statusCandidatesList = statusCandidatesRepository.findAll();
        assertThat(statusCandidatesList).hasSize(databaseSizeBeforeCreate);

        // Validate the StatusCandidates in Elasticsearch
        verify(mockStatusCandidatesSearchRepository, times(0)).save(statusCandidates);
    }

    @Test
    @Transactional
    public void getAllStatusCandidates() throws Exception {
        // Initialize the database
        statusCandidatesRepository.saveAndFlush(statusCandidates);

        // Get all the statusCandidatesList
        restStatusCandidatesMockMvc.perform(get("/api/status-candidates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusCandidates.getId().intValue())))
            .andExpect(jsonPath("$.[*].stageName").value(hasItem(DEFAULT_STAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].subStageName").value(hasItem(DEFAULT_SUB_STAGE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStatusCandidates() throws Exception {
        // Initialize the database
        statusCandidatesRepository.saveAndFlush(statusCandidates);

        // Get the statusCandidates
        restStatusCandidatesMockMvc.perform(get("/api/status-candidates/{id}", statusCandidates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statusCandidates.getId().intValue()))
            .andExpect(jsonPath("$.stageName").value(DEFAULT_STAGE_NAME.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.subStageName").value(DEFAULT_SUB_STAGE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatusCandidates() throws Exception {
        // Get the statusCandidates
        restStatusCandidatesMockMvc.perform(get("/api/status-candidates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusCandidates() throws Exception {
        // Initialize the database
        statusCandidatesRepository.saveAndFlush(statusCandidates);

        int databaseSizeBeforeUpdate = statusCandidatesRepository.findAll().size();

        // Update the statusCandidates
        StatusCandidates updatedStatusCandidates = statusCandidatesRepository.findById(statusCandidates.getId()).get();
        // Disconnect from session so that the updates on updatedStatusCandidates are not directly saved in db
        em.detach(updatedStatusCandidates);
        updatedStatusCandidates
            .stageName(UPDATED_STAGE_NAME)
            .source(UPDATED_SOURCE)
            .subStageName(UPDATED_SUB_STAGE_NAME);
        StatusCandidatesDTO statusCandidatesDTO = statusCandidatesMapper.toDto(updatedStatusCandidates);

        restStatusCandidatesMockMvc.perform(put("/api/status-candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusCandidatesDTO)))
            .andExpect(status().isOk());

        // Validate the StatusCandidates in the database
        List<StatusCandidates> statusCandidatesList = statusCandidatesRepository.findAll();
        assertThat(statusCandidatesList).hasSize(databaseSizeBeforeUpdate);
        StatusCandidates testStatusCandidates = statusCandidatesList.get(statusCandidatesList.size() - 1);
        assertThat(testStatusCandidates.getStageName()).isEqualTo(UPDATED_STAGE_NAME);
        assertThat(testStatusCandidates.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testStatusCandidates.getSubStageName()).isEqualTo(UPDATED_SUB_STAGE_NAME);

        // Validate the StatusCandidates in Elasticsearch
        verify(mockStatusCandidatesSearchRepository, times(1)).save(testStatusCandidates);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusCandidates() throws Exception {
        int databaseSizeBeforeUpdate = statusCandidatesRepository.findAll().size();

        // Create the StatusCandidates
        StatusCandidatesDTO statusCandidatesDTO = statusCandidatesMapper.toDto(statusCandidates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusCandidatesMockMvc.perform(put("/api/status-candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statusCandidatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StatusCandidates in the database
        List<StatusCandidates> statusCandidatesList = statusCandidatesRepository.findAll();
        assertThat(statusCandidatesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the StatusCandidates in Elasticsearch
        verify(mockStatusCandidatesSearchRepository, times(0)).save(statusCandidates);
    }

    @Test
    @Transactional
    public void deleteStatusCandidates() throws Exception {
        // Initialize the database
        statusCandidatesRepository.saveAndFlush(statusCandidates);

        int databaseSizeBeforeDelete = statusCandidatesRepository.findAll().size();

        // Get the statusCandidates
        restStatusCandidatesMockMvc.perform(delete("/api/status-candidates/{id}", statusCandidates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StatusCandidates> statusCandidatesList = statusCandidatesRepository.findAll();
        assertThat(statusCandidatesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the StatusCandidates in Elasticsearch
        verify(mockStatusCandidatesSearchRepository, times(1)).deleteById(statusCandidates.getId());
    }

    @Test
    @Transactional
    public void searchStatusCandidates() throws Exception {
        // Initialize the database
        statusCandidatesRepository.saveAndFlush(statusCandidates);
        when(mockStatusCandidatesSearchRepository.search(queryStringQuery("id:" + statusCandidates.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(statusCandidates), PageRequest.of(0, 1), 1));
        // Search the statusCandidates
        restStatusCandidatesMockMvc.perform(get("/api/_search/status-candidates?query=id:" + statusCandidates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusCandidates.getId().intValue())))
            .andExpect(jsonPath("$.[*].stageName").value(hasItem(DEFAULT_STAGE_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].subStageName").value(hasItem(DEFAULT_SUB_STAGE_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusCandidates.class);
        StatusCandidates statusCandidates1 = new StatusCandidates();
        statusCandidates1.setId(1L);
        StatusCandidates statusCandidates2 = new StatusCandidates();
        statusCandidates2.setId(statusCandidates1.getId());
        assertThat(statusCandidates1).isEqualTo(statusCandidates2);
        statusCandidates2.setId(2L);
        assertThat(statusCandidates1).isNotEqualTo(statusCandidates2);
        statusCandidates1.setId(null);
        assertThat(statusCandidates1).isNotEqualTo(statusCandidates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusCandidatesDTO.class);
        StatusCandidatesDTO statusCandidatesDTO1 = new StatusCandidatesDTO();
        statusCandidatesDTO1.setId(1L);
        StatusCandidatesDTO statusCandidatesDTO2 = new StatusCandidatesDTO();
        assertThat(statusCandidatesDTO1).isNotEqualTo(statusCandidatesDTO2);
        statusCandidatesDTO2.setId(statusCandidatesDTO1.getId());
        assertThat(statusCandidatesDTO1).isEqualTo(statusCandidatesDTO2);
        statusCandidatesDTO2.setId(2L);
        assertThat(statusCandidatesDTO1).isNotEqualTo(statusCandidatesDTO2);
        statusCandidatesDTO1.setId(null);
        assertThat(statusCandidatesDTO1).isNotEqualTo(statusCandidatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(statusCandidatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(statusCandidatesMapper.fromId(null)).isNull();
    }
}
