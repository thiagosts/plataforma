package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Candidates;
import br.com.connekt.plataforma.repository.CandidatesRepository;
import br.com.connekt.plataforma.repository.search.CandidatesSearchRepository;
import br.com.connekt.plataforma.service.CandidatesService;
import br.com.connekt.plataforma.service.dto.CandidatesDTO;
import br.com.connekt.plataforma.service.mapper.CandidatesMapper;
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
 * Test class for the CandidatesResource REST controller.
 *
 * @see CandidatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class CandidatesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CEL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CEL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_PICTURE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SALES_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SALES_FORCE_ID = "BBBBBBBBBB";

    @Autowired
    private CandidatesRepository candidatesRepository;

    @Autowired
    private CandidatesMapper candidatesMapper;

    @Autowired
    private CandidatesService candidatesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.CandidatesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CandidatesSearchRepository mockCandidatesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidatesMockMvc;

    private Candidates candidates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatesResource candidatesResource = new CandidatesResource(candidatesService);
        this.restCandidatesMockMvc = MockMvcBuilders.standaloneSetup(candidatesResource)
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
    public static Candidates createEntity(EntityManager em) {
        Candidates candidates = new Candidates()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .celPhone(DEFAULT_CEL_PHONE)
            .area(DEFAULT_AREA)
            .dataOfBirth(DEFAULT_DATA_OF_BIRTH)
            .occupation(DEFAULT_OCCUPATION)
            .pictureUrl(DEFAULT_PICTURE_URL)
            .salesForceId(DEFAULT_SALES_FORCE_ID);
        return candidates;
    }

    @Before
    public void initTest() {
        candidates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidates() throws Exception {
        int databaseSizeBeforeCreate = candidatesRepository.findAll().size();

        // Create the Candidates
        CandidatesDTO candidatesDTO = candidatesMapper.toDto(candidates);
        restCandidatesMockMvc.perform(post("/api/candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidates in the database
        List<Candidates> candidatesList = candidatesRepository.findAll();
        assertThat(candidatesList).hasSize(databaseSizeBeforeCreate + 1);
        Candidates testCandidates = candidatesList.get(candidatesList.size() - 1);
        assertThat(testCandidates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCandidates.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidates.getCelPhone()).isEqualTo(DEFAULT_CEL_PHONE);
        assertThat(testCandidates.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testCandidates.getDataOfBirth()).isEqualTo(DEFAULT_DATA_OF_BIRTH);
        assertThat(testCandidates.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testCandidates.getPictureUrl()).isEqualTo(DEFAULT_PICTURE_URL);
        assertThat(testCandidates.getSalesForceId()).isEqualTo(DEFAULT_SALES_FORCE_ID);

        // Validate the Candidates in Elasticsearch
        verify(mockCandidatesSearchRepository, times(1)).save(testCandidates);
    }

    @Test
    @Transactional
    public void createCandidatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatesRepository.findAll().size();

        // Create the Candidates with an existing ID
        candidates.setId(1L);
        CandidatesDTO candidatesDTO = candidatesMapper.toDto(candidates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatesMockMvc.perform(post("/api/candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidates in the database
        List<Candidates> candidatesList = candidatesRepository.findAll();
        assertThat(candidatesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Candidates in Elasticsearch
        verify(mockCandidatesSearchRepository, times(0)).save(candidates);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidatesRepository.saveAndFlush(candidates);

        // Get all the candidatesList
        restCandidatesMockMvc.perform(get("/api/candidates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].celPhone").value(hasItem(DEFAULT_CEL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].dataOfBirth").value(hasItem(DEFAULT_DATA_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].pictureUrl").value(hasItem(DEFAULT_PICTURE_URL.toString())))
            .andExpect(jsonPath("$.[*].salesForceId").value(hasItem(DEFAULT_SALES_FORCE_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getCandidates() throws Exception {
        // Initialize the database
        candidatesRepository.saveAndFlush(candidates);

        // Get the candidates
        restCandidatesMockMvc.perform(get("/api/candidates/{id}", candidates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.celPhone").value(DEFAULT_CEL_PHONE.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.dataOfBirth").value(DEFAULT_DATA_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.pictureUrl").value(DEFAULT_PICTURE_URL.toString()))
            .andExpect(jsonPath("$.salesForceId").value(DEFAULT_SALES_FORCE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidates() throws Exception {
        // Get the candidates
        restCandidatesMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidates() throws Exception {
        // Initialize the database
        candidatesRepository.saveAndFlush(candidates);

        int databaseSizeBeforeUpdate = candidatesRepository.findAll().size();

        // Update the candidates
        Candidates updatedCandidates = candidatesRepository.findById(candidates.getId()).get();
        // Disconnect from session so that the updates on updatedCandidates are not directly saved in db
        em.detach(updatedCandidates);
        updatedCandidates
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .celPhone(UPDATED_CEL_PHONE)
            .area(UPDATED_AREA)
            .dataOfBirth(UPDATED_DATA_OF_BIRTH)
            .occupation(UPDATED_OCCUPATION)
            .pictureUrl(UPDATED_PICTURE_URL)
            .salesForceId(UPDATED_SALES_FORCE_ID);
        CandidatesDTO candidatesDTO = candidatesMapper.toDto(updatedCandidates);

        restCandidatesMockMvc.perform(put("/api/candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatesDTO)))
            .andExpect(status().isOk());

        // Validate the Candidates in the database
        List<Candidates> candidatesList = candidatesRepository.findAll();
        assertThat(candidatesList).hasSize(databaseSizeBeforeUpdate);
        Candidates testCandidates = candidatesList.get(candidatesList.size() - 1);
        assertThat(testCandidates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCandidates.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidates.getCelPhone()).isEqualTo(UPDATED_CEL_PHONE);
        assertThat(testCandidates.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testCandidates.getDataOfBirth()).isEqualTo(UPDATED_DATA_OF_BIRTH);
        assertThat(testCandidates.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testCandidates.getPictureUrl()).isEqualTo(UPDATED_PICTURE_URL);
        assertThat(testCandidates.getSalesForceId()).isEqualTo(UPDATED_SALES_FORCE_ID);

        // Validate the Candidates in Elasticsearch
        verify(mockCandidatesSearchRepository, times(1)).save(testCandidates);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidates() throws Exception {
        int databaseSizeBeforeUpdate = candidatesRepository.findAll().size();

        // Create the Candidates
        CandidatesDTO candidatesDTO = candidatesMapper.toDto(candidates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatesMockMvc.perform(put("/api/candidates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidates in the database
        List<Candidates> candidatesList = candidatesRepository.findAll();
        assertThat(candidatesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Candidates in Elasticsearch
        verify(mockCandidatesSearchRepository, times(0)).save(candidates);
    }

    @Test
    @Transactional
    public void deleteCandidates() throws Exception {
        // Initialize the database
        candidatesRepository.saveAndFlush(candidates);

        int databaseSizeBeforeDelete = candidatesRepository.findAll().size();

        // Get the candidates
        restCandidatesMockMvc.perform(delete("/api/candidates/{id}", candidates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidates> candidatesList = candidatesRepository.findAll();
        assertThat(candidatesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Candidates in Elasticsearch
        verify(mockCandidatesSearchRepository, times(1)).deleteById(candidates.getId());
    }

    @Test
    @Transactional
    public void searchCandidates() throws Exception {
        // Initialize the database
        candidatesRepository.saveAndFlush(candidates);
        when(mockCandidatesSearchRepository.search(queryStringQuery("id:" + candidates.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(candidates), PageRequest.of(0, 1), 1));
        // Search the candidates
        restCandidatesMockMvc.perform(get("/api/_search/candidates?query=id:" + candidates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].celPhone").value(hasItem(DEFAULT_CEL_PHONE)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].dataOfBirth").value(hasItem(DEFAULT_DATA_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].pictureUrl").value(hasItem(DEFAULT_PICTURE_URL)))
            .andExpect(jsonPath("$.[*].salesForceId").value(hasItem(DEFAULT_SALES_FORCE_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidates.class);
        Candidates candidates1 = new Candidates();
        candidates1.setId(1L);
        Candidates candidates2 = new Candidates();
        candidates2.setId(candidates1.getId());
        assertThat(candidates1).isEqualTo(candidates2);
        candidates2.setId(2L);
        assertThat(candidates1).isNotEqualTo(candidates2);
        candidates1.setId(null);
        assertThat(candidates1).isNotEqualTo(candidates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatesDTO.class);
        CandidatesDTO candidatesDTO1 = new CandidatesDTO();
        candidatesDTO1.setId(1L);
        CandidatesDTO candidatesDTO2 = new CandidatesDTO();
        assertThat(candidatesDTO1).isNotEqualTo(candidatesDTO2);
        candidatesDTO2.setId(candidatesDTO1.getId());
        assertThat(candidatesDTO1).isEqualTo(candidatesDTO2);
        candidatesDTO2.setId(2L);
        assertThat(candidatesDTO1).isNotEqualTo(candidatesDTO2);
        candidatesDTO1.setId(null);
        assertThat(candidatesDTO1).isNotEqualTo(candidatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(candidatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(candidatesMapper.fromId(null)).isNull();
    }
}
