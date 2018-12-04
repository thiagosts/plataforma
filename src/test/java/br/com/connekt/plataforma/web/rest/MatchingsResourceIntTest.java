package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Matchings;
import br.com.connekt.plataforma.repository.MatchingsRepository;
import br.com.connekt.plataforma.repository.search.MatchingsSearchRepository;
import br.com.connekt.plataforma.service.MatchingsService;
import br.com.connekt.plataforma.service.dto.MatchingsDTO;
import br.com.connekt.plataforma.service.mapper.MatchingsMapper;
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
 * Test class for the MatchingsResource REST controller.
 *
 * @see MatchingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class MatchingsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_TIME = new BigDecimal(1);
    private static final BigDecimal UPDATED_TIME = new BigDecimal(2);

    private static final String DEFAULT_IS_DEFAULT = "AAAAAAAAAA";
    private static final String UPDATED_IS_DEFAULT = "BBBBBBBBBB";

    @Autowired
    private MatchingsRepository matchingsRepository;

    @Autowired
    private MatchingsMapper matchingsMapper;

    @Autowired
    private MatchingsService matchingsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.MatchingsSearchRepositoryMockConfiguration
     */
    @Autowired
    private MatchingsSearchRepository mockMatchingsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMatchingsMockMvc;

    private Matchings matchings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MatchingsResource matchingsResource = new MatchingsResource(matchingsService);
        this.restMatchingsMockMvc = MockMvcBuilders.standaloneSetup(matchingsResource)
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
    public static Matchings createEntity(EntityManager em) {
        Matchings matchings = new Matchings()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .time(DEFAULT_TIME)
            .isDefault(DEFAULT_IS_DEFAULT);
        return matchings;
    }

    @Before
    public void initTest() {
        matchings = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatchings() throws Exception {
        int databaseSizeBeforeCreate = matchingsRepository.findAll().size();

        // Create the Matchings
        MatchingsDTO matchingsDTO = matchingsMapper.toDto(matchings);
        restMatchingsMockMvc.perform(post("/api/matchings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchingsDTO)))
            .andExpect(status().isCreated());

        // Validate the Matchings in the database
        List<Matchings> matchingsList = matchingsRepository.findAll();
        assertThat(matchingsList).hasSize(databaseSizeBeforeCreate + 1);
        Matchings testMatchings = matchingsList.get(matchingsList.size() - 1);
        assertThat(testMatchings.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMatchings.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMatchings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMatchings.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testMatchings.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testMatchings.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);

        // Validate the Matchings in Elasticsearch
        verify(mockMatchingsSearchRepository, times(1)).save(testMatchings);
    }

    @Test
    @Transactional
    public void createMatchingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matchingsRepository.findAll().size();

        // Create the Matchings with an existing ID
        matchings.setId(1L);
        MatchingsDTO matchingsDTO = matchingsMapper.toDto(matchings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchingsMockMvc.perform(post("/api/matchings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matchings in the database
        List<Matchings> matchingsList = matchingsRepository.findAll();
        assertThat(matchingsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Matchings in Elasticsearch
        verify(mockMatchingsSearchRepository, times(0)).save(matchings);
    }

    @Test
    @Transactional
    public void getAllMatchings() throws Exception {
        // Initialize the database
        matchingsRepository.saveAndFlush(matchings);

        // Get all the matchingsList
        restMatchingsMockMvc.perform(get("/api/matchings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.toString())));
    }
    
    @Test
    @Transactional
    public void getMatchings() throws Exception {
        // Initialize the database
        matchingsRepository.saveAndFlush(matchings);

        // Get the matchings
        restMatchingsMockMvc.perform(get("/api/matchings/{id}", matchings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(matchings.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.intValue()))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMatchings() throws Exception {
        // Get the matchings
        restMatchingsMockMvc.perform(get("/api/matchings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatchings() throws Exception {
        // Initialize the database
        matchingsRepository.saveAndFlush(matchings);

        int databaseSizeBeforeUpdate = matchingsRepository.findAll().size();

        // Update the matchings
        Matchings updatedMatchings = matchingsRepository.findById(matchings.getId()).get();
        // Disconnect from session so that the updates on updatedMatchings are not directly saved in db
        em.detach(updatedMatchings);
        updatedMatchings
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .time(UPDATED_TIME)
            .isDefault(UPDATED_IS_DEFAULT);
        MatchingsDTO matchingsDTO = matchingsMapper.toDto(updatedMatchings);

        restMatchingsMockMvc.perform(put("/api/matchings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchingsDTO)))
            .andExpect(status().isOk());

        // Validate the Matchings in the database
        List<Matchings> matchingsList = matchingsRepository.findAll();
        assertThat(matchingsList).hasSize(databaseSizeBeforeUpdate);
        Matchings testMatchings = matchingsList.get(matchingsList.size() - 1);
        assertThat(testMatchings.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMatchings.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMatchings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMatchings.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testMatchings.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testMatchings.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);

        // Validate the Matchings in Elasticsearch
        verify(mockMatchingsSearchRepository, times(1)).save(testMatchings);
    }

    @Test
    @Transactional
    public void updateNonExistingMatchings() throws Exception {
        int databaseSizeBeforeUpdate = matchingsRepository.findAll().size();

        // Create the Matchings
        MatchingsDTO matchingsDTO = matchingsMapper.toDto(matchings);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchingsMockMvc.perform(put("/api/matchings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(matchingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matchings in the database
        List<Matchings> matchingsList = matchingsRepository.findAll();
        assertThat(matchingsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Matchings in Elasticsearch
        verify(mockMatchingsSearchRepository, times(0)).save(matchings);
    }

    @Test
    @Transactional
    public void deleteMatchings() throws Exception {
        // Initialize the database
        matchingsRepository.saveAndFlush(matchings);

        int databaseSizeBeforeDelete = matchingsRepository.findAll().size();

        // Get the matchings
        restMatchingsMockMvc.perform(delete("/api/matchings/{id}", matchings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Matchings> matchingsList = matchingsRepository.findAll();
        assertThat(matchingsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Matchings in Elasticsearch
        verify(mockMatchingsSearchRepository, times(1)).deleteById(matchings.getId());
    }

    @Test
    @Transactional
    public void searchMatchings() throws Exception {
        // Initialize the database
        matchingsRepository.saveAndFlush(matchings);
        when(mockMatchingsSearchRepository.search(queryStringQuery("id:" + matchings.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(matchings), PageRequest.of(0, 1), 1));
        // Search the matchings
        restMatchingsMockMvc.perform(get("/api/_search/matchings?query=id:" + matchings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchings.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Matchings.class);
        Matchings matchings1 = new Matchings();
        matchings1.setId(1L);
        Matchings matchings2 = new Matchings();
        matchings2.setId(matchings1.getId());
        assertThat(matchings1).isEqualTo(matchings2);
        matchings2.setId(2L);
        assertThat(matchings1).isNotEqualTo(matchings2);
        matchings1.setId(null);
        assertThat(matchings1).isNotEqualTo(matchings2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchingsDTO.class);
        MatchingsDTO matchingsDTO1 = new MatchingsDTO();
        matchingsDTO1.setId(1L);
        MatchingsDTO matchingsDTO2 = new MatchingsDTO();
        assertThat(matchingsDTO1).isNotEqualTo(matchingsDTO2);
        matchingsDTO2.setId(matchingsDTO1.getId());
        assertThat(matchingsDTO1).isEqualTo(matchingsDTO2);
        matchingsDTO2.setId(2L);
        assertThat(matchingsDTO1).isNotEqualTo(matchingsDTO2);
        matchingsDTO1.setId(null);
        assertThat(matchingsDTO1).isNotEqualTo(matchingsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(matchingsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(matchingsMapper.fromId(null)).isNull();
    }
}
