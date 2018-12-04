package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Benefits;
import br.com.connekt.plataforma.repository.BenefitsRepository;
import br.com.connekt.plataforma.repository.search.BenefitsSearchRepository;
import br.com.connekt.plataforma.service.BenefitsService;
import br.com.connekt.plataforma.service.dto.BenefitsDTO;
import br.com.connekt.plataforma.service.mapper.BenefitsMapper;
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
 * Test class for the BenefitsResource REST controller.
 *
 * @see BenefitsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class BenefitsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private BenefitsRepository benefitsRepository;

    @Autowired
    private BenefitsMapper benefitsMapper;

    @Autowired
    private BenefitsService benefitsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.BenefitsSearchRepositoryMockConfiguration
     */
    @Autowired
    private BenefitsSearchRepository mockBenefitsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBenefitsMockMvc;

    private Benefits benefits;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BenefitsResource benefitsResource = new BenefitsResource(benefitsService);
        this.restBenefitsMockMvc = MockMvcBuilders.standaloneSetup(benefitsResource)
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
    public static Benefits createEntity(EntityManager em) {
        Benefits benefits = new Benefits()
            .name(DEFAULT_NAME)
            .icon(DEFAULT_ICON);
        return benefits;
    }

    @Before
    public void initTest() {
        benefits = createEntity(em);
    }

    @Test
    @Transactional
    public void createBenefits() throws Exception {
        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();

        // Create the Benefits
        BenefitsDTO benefitsDTO = benefitsMapper.toDto(benefits);
        restBenefitsMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitsDTO)))
            .andExpect(status().isCreated());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate + 1);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefits.getIcon()).isEqualTo(DEFAULT_ICON);

        // Validate the Benefits in Elasticsearch
        verify(mockBenefitsSearchRepository, times(1)).save(testBenefits);
    }

    @Test
    @Transactional
    public void createBenefitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = benefitsRepository.findAll().size();

        // Create the Benefits with an existing ID
        benefits.setId(1L);
        BenefitsDTO benefitsDTO = benefitsMapper.toDto(benefits);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitsMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Benefits in Elasticsearch
        verify(mockBenefitsSearchRepository, times(0)).save(benefits);
    }

    @Test
    @Transactional
    public void getAllBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get all the benefitsList
        restBenefitsMockMvc.perform(get("/api/benefits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())));
    }
    
    @Test
    @Transactional
    public void getBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        // Get the benefits
        restBenefitsMockMvc.perform(get("/api/benefits/{id}", benefits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(benefits.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBenefits() throws Exception {
        // Get the benefits
        restBenefitsMockMvc.perform(get("/api/benefits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Update the benefits
        Benefits updatedBenefits = benefitsRepository.findById(benefits.getId()).get();
        // Disconnect from session so that the updates on updatedBenefits are not directly saved in db
        em.detach(updatedBenefits);
        updatedBenefits
            .name(UPDATED_NAME)
            .icon(UPDATED_ICON);
        BenefitsDTO benefitsDTO = benefitsMapper.toDto(updatedBenefits);

        restBenefitsMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitsDTO)))
            .andExpect(status().isOk());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);
        Benefits testBenefits = benefitsList.get(benefitsList.size() - 1);
        assertThat(testBenefits.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefits.getIcon()).isEqualTo(UPDATED_ICON);

        // Validate the Benefits in Elasticsearch
        verify(mockBenefitsSearchRepository, times(1)).save(testBenefits);
    }

    @Test
    @Transactional
    public void updateNonExistingBenefits() throws Exception {
        int databaseSizeBeforeUpdate = benefitsRepository.findAll().size();

        // Create the Benefits
        BenefitsDTO benefitsDTO = benefitsMapper.toDto(benefits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitsMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Benefits in the database
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Benefits in Elasticsearch
        verify(mockBenefitsSearchRepository, times(0)).save(benefits);
    }

    @Test
    @Transactional
    public void deleteBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);

        int databaseSizeBeforeDelete = benefitsRepository.findAll().size();

        // Get the benefits
        restBenefitsMockMvc.perform(delete("/api/benefits/{id}", benefits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Benefits> benefitsList = benefitsRepository.findAll();
        assertThat(benefitsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Benefits in Elasticsearch
        verify(mockBenefitsSearchRepository, times(1)).deleteById(benefits.getId());
    }

    @Test
    @Transactional
    public void searchBenefits() throws Exception {
        // Initialize the database
        benefitsRepository.saveAndFlush(benefits);
        when(mockBenefitsSearchRepository.search(queryStringQuery("id:" + benefits.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(benefits), PageRequest.of(0, 1), 1));
        // Search the benefits
        restBenefitsMockMvc.perform(get("/api/_search/benefits?query=id:" + benefits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefits.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benefits.class);
        Benefits benefits1 = new Benefits();
        benefits1.setId(1L);
        Benefits benefits2 = new Benefits();
        benefits2.setId(benefits1.getId());
        assertThat(benefits1).isEqualTo(benefits2);
        benefits2.setId(2L);
        assertThat(benefits1).isNotEqualTo(benefits2);
        benefits1.setId(null);
        assertThat(benefits1).isNotEqualTo(benefits2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitsDTO.class);
        BenefitsDTO benefitsDTO1 = new BenefitsDTO();
        benefitsDTO1.setId(1L);
        BenefitsDTO benefitsDTO2 = new BenefitsDTO();
        assertThat(benefitsDTO1).isNotEqualTo(benefitsDTO2);
        benefitsDTO2.setId(benefitsDTO1.getId());
        assertThat(benefitsDTO1).isEqualTo(benefitsDTO2);
        benefitsDTO2.setId(2L);
        assertThat(benefitsDTO1).isNotEqualTo(benefitsDTO2);
        benefitsDTO1.setId(null);
        assertThat(benefitsDTO1).isNotEqualTo(benefitsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(benefitsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(benefitsMapper.fromId(null)).isNull();
    }
}
