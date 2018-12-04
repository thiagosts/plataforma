package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Templates;
import br.com.connekt.plataforma.repository.TemplatesRepository;
import br.com.connekt.plataforma.repository.search.TemplatesSearchRepository;
import br.com.connekt.plataforma.service.TemplatesService;
import br.com.connekt.plataforma.service.dto.TemplatesDTO;
import br.com.connekt.plataforma.service.mapper.TemplatesMapper;
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
 * Test class for the TemplatesResource REST controller.
 *
 * @see TemplatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class TemplatesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_CSS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_CSS = "BBBBBBBBBB";

    @Autowired
    private TemplatesRepository templatesRepository;

    @Autowired
    private TemplatesMapper templatesMapper;

    @Autowired
    private TemplatesService templatesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.TemplatesSearchRepositoryMockConfiguration
     */
    @Autowired
    private TemplatesSearchRepository mockTemplatesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplatesMockMvc;

    private Templates templates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplatesResource templatesResource = new TemplatesResource(templatesService);
        this.restTemplatesMockMvc = MockMvcBuilders.standaloneSetup(templatesResource)
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
    public static Templates createEntity(EntityManager em) {
        Templates templates = new Templates()
            .name(DEFAULT_NAME)
            .customCss(DEFAULT_CUSTOM_CSS);
        return templates;
    }

    @Before
    public void initTest() {
        templates = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplates() throws Exception {
        int databaseSizeBeforeCreate = templatesRepository.findAll().size();

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);
        restTemplatesMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate + 1);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplates.getCustomCss()).isEqualTo(DEFAULT_CUSTOM_CSS);

        // Validate the Templates in Elasticsearch
        verify(mockTemplatesSearchRepository, times(1)).save(testTemplates);
    }

    @Test
    @Transactional
    public void createTemplatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templatesRepository.findAll().size();

        // Create the Templates with an existing ID
        templates.setId(1L);
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplatesMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Templates in Elasticsearch
        verify(mockTemplatesSearchRepository, times(0)).save(templates);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList
        restTemplatesMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].customCss").value(hasItem(DEFAULT_CUSTOM_CSS.toString())));
    }
    
    @Test
    @Transactional
    public void getTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get the templates
        restTemplatesMockMvc.perform(get("/api/templates/{id}", templates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(templates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.customCss").value(DEFAULT_CUSTOM_CSS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTemplates() throws Exception {
        // Get the templates
        restTemplatesMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Update the templates
        Templates updatedTemplates = templatesRepository.findById(templates.getId()).get();
        // Disconnect from session so that the updates on updatedTemplates are not directly saved in db
        em.detach(updatedTemplates);
        updatedTemplates
            .name(UPDATED_NAME)
            .customCss(UPDATED_CUSTOM_CSS);
        TemplatesDTO templatesDTO = templatesMapper.toDto(updatedTemplates);

        restTemplatesMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isOk());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplates.getCustomCss()).isEqualTo(UPDATED_CUSTOM_CSS);

        // Validate the Templates in Elasticsearch
        verify(mockTemplatesSearchRepository, times(1)).save(testTemplates);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplatesMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Templates in Elasticsearch
        verify(mockTemplatesSearchRepository, times(0)).save(templates);
    }

    @Test
    @Transactional
    public void deleteTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeDelete = templatesRepository.findAll().size();

        // Get the templates
        restTemplatesMockMvc.perform(delete("/api/templates/{id}", templates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Templates in Elasticsearch
        verify(mockTemplatesSearchRepository, times(1)).deleteById(templates.getId());
    }

    @Test
    @Transactional
    public void searchTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);
        when(mockTemplatesSearchRepository.search(queryStringQuery("id:" + templates.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(templates), PageRequest.of(0, 1), 1));
        // Search the templates
        restTemplatesMockMvc.perform(get("/api/_search/templates?query=id:" + templates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].customCss").value(hasItem(DEFAULT_CUSTOM_CSS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Templates.class);
        Templates templates1 = new Templates();
        templates1.setId(1L);
        Templates templates2 = new Templates();
        templates2.setId(templates1.getId());
        assertThat(templates1).isEqualTo(templates2);
        templates2.setId(2L);
        assertThat(templates1).isNotEqualTo(templates2);
        templates1.setId(null);
        assertThat(templates1).isNotEqualTo(templates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplatesDTO.class);
        TemplatesDTO templatesDTO1 = new TemplatesDTO();
        templatesDTO1.setId(1L);
        TemplatesDTO templatesDTO2 = new TemplatesDTO();
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
        templatesDTO2.setId(templatesDTO1.getId());
        assertThat(templatesDTO1).isEqualTo(templatesDTO2);
        templatesDTO2.setId(2L);
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
        templatesDTO1.setId(null);
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(templatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(templatesMapper.fromId(null)).isNull();
    }
}
