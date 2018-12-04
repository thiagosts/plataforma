package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Opportunities;
import br.com.connekt.plataforma.repository.OpportunitiesRepository;
import br.com.connekt.plataforma.repository.search.OpportunitiesSearchRepository;
import br.com.connekt.plataforma.service.OpportunitiesService;
import br.com.connekt.plataforma.service.dto.OpportunitiesDTO;
import br.com.connekt.plataforma.service.mapper.OpportunitiesMapper;
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

import br.com.connekt.plataforma.domain.enumeration.OpportunitiesTypeEnums;
/**
 * Test class for the OpportunitiesResource REST controller.
 *
 * @see OpportunitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class OpportunitiesResourceIntTest {

    private static final String DEFAULT_OPPORTUNITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPPORTUNITY_CODE = "BBBBBBBBBB";

    private static final OpportunitiesTypeEnums DEFAULT_OPPORTUNITIES_TYPE = OpportunitiesTypeEnums.INTERNAL;
    private static final OpportunitiesTypeEnums UPDATED_OPPORTUNITIES_TYPE = OpportunitiesTypeEnums.EXTERNAL;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HIGHLIGHTED = false;
    private static final Boolean UPDATED_HIGHLIGHTED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_LOGO_DESKTOP_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_DESKTOP_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_MOBILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_MOBILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_HIRING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_HIRING_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    @Autowired
    private OpportunitiesRepository opportunitiesRepository;

    @Autowired
    private OpportunitiesMapper opportunitiesMapper;

    @Autowired
    private OpportunitiesService opportunitiesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.OpportunitiesSearchRepositoryMockConfiguration
     */
    @Autowired
    private OpportunitiesSearchRepository mockOpportunitiesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOpportunitiesMockMvc;

    private Opportunities opportunities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpportunitiesResource opportunitiesResource = new OpportunitiesResource(opportunitiesService);
        this.restOpportunitiesMockMvc = MockMvcBuilders.standaloneSetup(opportunitiesResource)
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
    public static Opportunities createEntity(EntityManager em) {
        Opportunities opportunities = new Opportunities()
            .opportunityCode(DEFAULT_OPPORTUNITY_CODE)
            .opportunitiesType(DEFAULT_OPPORTUNITIES_TYPE)
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .area(DEFAULT_AREA)
            .externalId(DEFAULT_EXTERNAL_ID)
            .highlighted(DEFAULT_HIGHLIGHTED)
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .quantity(DEFAULT_QUANTITY)
            .logoDesktopUrl(DEFAULT_LOGO_DESKTOP_URL)
            .logoMobileUrl(DEFAULT_LOGO_MOBILE_URL)
            .hiringType(DEFAULT_HIRING_TYPE)
            .slug(DEFAULT_SLUG);
        return opportunities;
    }

    @Before
    public void initTest() {
        opportunities = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpportunities() throws Exception {
        int databaseSizeBeforeCreate = opportunitiesRepository.findAll().size();

        // Create the Opportunities
        OpportunitiesDTO opportunitiesDTO = opportunitiesMapper.toDto(opportunities);
        restOpportunitiesMockMvc.perform(post("/api/opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunitiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Opportunities in the database
        List<Opportunities> opportunitiesList = opportunitiesRepository.findAll();
        assertThat(opportunitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Opportunities testOpportunities = opportunitiesList.get(opportunitiesList.size() - 1);
        assertThat(testOpportunities.getOpportunityCode()).isEqualTo(DEFAULT_OPPORTUNITY_CODE);
        assertThat(testOpportunities.getOpportunitiesType()).isEqualTo(DEFAULT_OPPORTUNITIES_TYPE);
        assertThat(testOpportunities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOpportunities.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOpportunities.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testOpportunities.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testOpportunities.isHighlighted()).isEqualTo(DEFAULT_HIGHLIGHTED);
        assertThat(testOpportunities.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOpportunities.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOpportunities.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOpportunities.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOpportunities.getLogoDesktopUrl()).isEqualTo(DEFAULT_LOGO_DESKTOP_URL);
        assertThat(testOpportunities.getLogoMobileUrl()).isEqualTo(DEFAULT_LOGO_MOBILE_URL);
        assertThat(testOpportunities.getHiringType()).isEqualTo(DEFAULT_HIRING_TYPE);
        assertThat(testOpportunities.getSlug()).isEqualTo(DEFAULT_SLUG);

        // Validate the Opportunities in Elasticsearch
        verify(mockOpportunitiesSearchRepository, times(1)).save(testOpportunities);
    }

    @Test
    @Transactional
    public void createOpportunitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opportunitiesRepository.findAll().size();

        // Create the Opportunities with an existing ID
        opportunities.setId(1L);
        OpportunitiesDTO opportunitiesDTO = opportunitiesMapper.toDto(opportunities);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunitiesMockMvc.perform(post("/api/opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunitiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Opportunities in the database
        List<Opportunities> opportunitiesList = opportunitiesRepository.findAll();
        assertThat(opportunitiesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Opportunities in Elasticsearch
        verify(mockOpportunitiesSearchRepository, times(0)).save(opportunities);
    }

    @Test
    @Transactional
    public void getAllOpportunities() throws Exception {
        // Initialize the database
        opportunitiesRepository.saveAndFlush(opportunities);

        // Get all the opportunitiesList
        restOpportunitiesMockMvc.perform(get("/api/opportunities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunities.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityCode").value(hasItem(DEFAULT_OPPORTUNITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].opportunitiesType").value(hasItem(DEFAULT_OPPORTUNITIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID.toString())))
            .andExpect(jsonPath("$.[*].highlighted").value(hasItem(DEFAULT_HIGHLIGHTED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].logoDesktopUrl").value(hasItem(DEFAULT_LOGO_DESKTOP_URL.toString())))
            .andExpect(jsonPath("$.[*].logoMobileUrl").value(hasItem(DEFAULT_LOGO_MOBILE_URL.toString())))
            .andExpect(jsonPath("$.[*].hiringType").value(hasItem(DEFAULT_HIRING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())));
    }
    
    @Test
    @Transactional
    public void getOpportunities() throws Exception {
        // Initialize the database
        opportunitiesRepository.saveAndFlush(opportunities);

        // Get the opportunities
        restOpportunitiesMockMvc.perform(get("/api/opportunities/{id}", opportunities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(opportunities.getId().intValue()))
            .andExpect(jsonPath("$.opportunityCode").value(DEFAULT_OPPORTUNITY_CODE.toString()))
            .andExpect(jsonPath("$.opportunitiesType").value(DEFAULT_OPPORTUNITIES_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID.toString()))
            .andExpect(jsonPath("$.highlighted").value(DEFAULT_HIGHLIGHTED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.logoDesktopUrl").value(DEFAULT_LOGO_DESKTOP_URL.toString()))
            .andExpect(jsonPath("$.logoMobileUrl").value(DEFAULT_LOGO_MOBILE_URL.toString()))
            .andExpect(jsonPath("$.hiringType").value(DEFAULT_HIRING_TYPE.toString()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOpportunities() throws Exception {
        // Get the opportunities
        restOpportunitiesMockMvc.perform(get("/api/opportunities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpportunities() throws Exception {
        // Initialize the database
        opportunitiesRepository.saveAndFlush(opportunities);

        int databaseSizeBeforeUpdate = opportunitiesRepository.findAll().size();

        // Update the opportunities
        Opportunities updatedOpportunities = opportunitiesRepository.findById(opportunities.getId()).get();
        // Disconnect from session so that the updates on updatedOpportunities are not directly saved in db
        em.detach(updatedOpportunities);
        updatedOpportunities
            .opportunityCode(UPDATED_OPPORTUNITY_CODE)
            .opportunitiesType(UPDATED_OPPORTUNITIES_TYPE)
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .area(UPDATED_AREA)
            .externalId(UPDATED_EXTERNAL_ID)
            .highlighted(UPDATED_HIGHLIGHTED)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .quantity(UPDATED_QUANTITY)
            .logoDesktopUrl(UPDATED_LOGO_DESKTOP_URL)
            .logoMobileUrl(UPDATED_LOGO_MOBILE_URL)
            .hiringType(UPDATED_HIRING_TYPE)
            .slug(UPDATED_SLUG);
        OpportunitiesDTO opportunitiesDTO = opportunitiesMapper.toDto(updatedOpportunities);

        restOpportunitiesMockMvc.perform(put("/api/opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunitiesDTO)))
            .andExpect(status().isOk());

        // Validate the Opportunities in the database
        List<Opportunities> opportunitiesList = opportunitiesRepository.findAll();
        assertThat(opportunitiesList).hasSize(databaseSizeBeforeUpdate);
        Opportunities testOpportunities = opportunitiesList.get(opportunitiesList.size() - 1);
        assertThat(testOpportunities.getOpportunityCode()).isEqualTo(UPDATED_OPPORTUNITY_CODE);
        assertThat(testOpportunities.getOpportunitiesType()).isEqualTo(UPDATED_OPPORTUNITIES_TYPE);
        assertThat(testOpportunities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOpportunities.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOpportunities.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testOpportunities.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testOpportunities.isHighlighted()).isEqualTo(UPDATED_HIGHLIGHTED);
        assertThat(testOpportunities.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOpportunities.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOpportunities.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOpportunities.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOpportunities.getLogoDesktopUrl()).isEqualTo(UPDATED_LOGO_DESKTOP_URL);
        assertThat(testOpportunities.getLogoMobileUrl()).isEqualTo(UPDATED_LOGO_MOBILE_URL);
        assertThat(testOpportunities.getHiringType()).isEqualTo(UPDATED_HIRING_TYPE);
        assertThat(testOpportunities.getSlug()).isEqualTo(UPDATED_SLUG);

        // Validate the Opportunities in Elasticsearch
        verify(mockOpportunitiesSearchRepository, times(1)).save(testOpportunities);
    }

    @Test
    @Transactional
    public void updateNonExistingOpportunities() throws Exception {
        int databaseSizeBeforeUpdate = opportunitiesRepository.findAll().size();

        // Create the Opportunities
        OpportunitiesDTO opportunitiesDTO = opportunitiesMapper.toDto(opportunities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunitiesMockMvc.perform(put("/api/opportunities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunitiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Opportunities in the database
        List<Opportunities> opportunitiesList = opportunitiesRepository.findAll();
        assertThat(opportunitiesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Opportunities in Elasticsearch
        verify(mockOpportunitiesSearchRepository, times(0)).save(opportunities);
    }

    @Test
    @Transactional
    public void deleteOpportunities() throws Exception {
        // Initialize the database
        opportunitiesRepository.saveAndFlush(opportunities);

        int databaseSizeBeforeDelete = opportunitiesRepository.findAll().size();

        // Get the opportunities
        restOpportunitiesMockMvc.perform(delete("/api/opportunities/{id}", opportunities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Opportunities> opportunitiesList = opportunitiesRepository.findAll();
        assertThat(opportunitiesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Opportunities in Elasticsearch
        verify(mockOpportunitiesSearchRepository, times(1)).deleteById(opportunities.getId());
    }

    @Test
    @Transactional
    public void searchOpportunities() throws Exception {
        // Initialize the database
        opportunitiesRepository.saveAndFlush(opportunities);
        when(mockOpportunitiesSearchRepository.search(queryStringQuery("id:" + opportunities.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(opportunities), PageRequest.of(0, 1), 1));
        // Search the opportunities
        restOpportunitiesMockMvc.perform(get("/api/_search/opportunities?query=id:" + opportunities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunities.getId().intValue())))
            .andExpect(jsonPath("$.[*].opportunityCode").value(hasItem(DEFAULT_OPPORTUNITY_CODE)))
            .andExpect(jsonPath("$.[*].opportunitiesType").value(hasItem(DEFAULT_OPPORTUNITIES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].highlighted").value(hasItem(DEFAULT_HIGHLIGHTED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].logoDesktopUrl").value(hasItem(DEFAULT_LOGO_DESKTOP_URL)))
            .andExpect(jsonPath("$.[*].logoMobileUrl").value(hasItem(DEFAULT_LOGO_MOBILE_URL)))
            .andExpect(jsonPath("$.[*].hiringType").value(hasItem(DEFAULT_HIRING_TYPE)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opportunities.class);
        Opportunities opportunities1 = new Opportunities();
        opportunities1.setId(1L);
        Opportunities opportunities2 = new Opportunities();
        opportunities2.setId(opportunities1.getId());
        assertThat(opportunities1).isEqualTo(opportunities2);
        opportunities2.setId(2L);
        assertThat(opportunities1).isNotEqualTo(opportunities2);
        opportunities1.setId(null);
        assertThat(opportunities1).isNotEqualTo(opportunities2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpportunitiesDTO.class);
        OpportunitiesDTO opportunitiesDTO1 = new OpportunitiesDTO();
        opportunitiesDTO1.setId(1L);
        OpportunitiesDTO opportunitiesDTO2 = new OpportunitiesDTO();
        assertThat(opportunitiesDTO1).isNotEqualTo(opportunitiesDTO2);
        opportunitiesDTO2.setId(opportunitiesDTO1.getId());
        assertThat(opportunitiesDTO1).isEqualTo(opportunitiesDTO2);
        opportunitiesDTO2.setId(2L);
        assertThat(opportunitiesDTO1).isNotEqualTo(opportunitiesDTO2);
        opportunitiesDTO1.setId(null);
        assertThat(opportunitiesDTO1).isNotEqualTo(opportunitiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(opportunitiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(opportunitiesMapper.fromId(null)).isNull();
    }
}
