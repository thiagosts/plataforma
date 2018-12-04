package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Resources;
import br.com.connekt.plataforma.repository.ResourcesRepository;
import br.com.connekt.plataforma.repository.search.ResourcesSearchRepository;
import br.com.connekt.plataforma.service.ResourcesService;
import br.com.connekt.plataforma.service.dto.ResourcesDTO;
import br.com.connekt.plataforma.service.mapper.ResourcesMapper;
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
 * Test class for the ResourcesResource REST controller.
 *
 * @see ResourcesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class ResourcesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_URL = "AAAAAAAAAA";
    private static final String UPDATED_ICON_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private ResourcesService resourcesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.ResourcesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ResourcesSearchRepository mockResourcesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourcesMockMvc;

    private Resources resources;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourcesResource resourcesResource = new ResourcesResource(resourcesService);
        this.restResourcesMockMvc = MockMvcBuilders.standaloneSetup(resourcesResource)
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
    public static Resources createEntity(EntityManager em) {
        Resources resources = new Resources()
            .name(DEFAULT_NAME)
            .linkUrl(DEFAULT_LINK_URL)
            .iconUrl(DEFAULT_ICON_URL)
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return resources;
    }

    @Before
    public void initTest() {
        resources = createEntity(em);
    }

    @Test
    @Transactional
    public void createResources() throws Exception {
        int databaseSizeBeforeCreate = resourcesRepository.findAll().size();

        // Create the Resources
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);
        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isCreated());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate + 1);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResources.getLinkUrl()).isEqualTo(DEFAULT_LINK_URL);
        assertThat(testResources.getIconUrl()).isEqualTo(DEFAULT_ICON_URL);
        assertThat(testResources.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResources.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testResources.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the Resources in Elasticsearch
        verify(mockResourcesSearchRepository, times(1)).save(testResources);
    }

    @Test
    @Transactional
    public void createResourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourcesRepository.findAll().size();

        // Create the Resources with an existing ID
        resources.setId(1L);
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourcesMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Resources in Elasticsearch
        verify(mockResourcesSearchRepository, times(0)).save(resources);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get all the resourcesList
        restResourcesMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resources.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].linkUrl").value(hasItem(DEFAULT_LINK_URL.toString())))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        // Get the resources
        restResourcesMockMvc.perform(get("/api/resources/{id}", resources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resources.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.linkUrl").value(DEFAULT_LINK_URL.toString()))
            .andExpect(jsonPath("$.iconUrl").value(DEFAULT_ICON_URL.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResources() throws Exception {
        // Get the resources
        restResourcesMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        int databaseSizeBeforeUpdate = resourcesRepository.findAll().size();

        // Update the resources
        Resources updatedResources = resourcesRepository.findById(resources.getId()).get();
        // Disconnect from session so that the updates on updatedResources are not directly saved in db
        em.detach(updatedResources);
        updatedResources
            .name(UPDATED_NAME)
            .linkUrl(UPDATED_LINK_URL)
            .iconUrl(UPDATED_ICON_URL)
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(updatedResources);

        restResourcesMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isOk());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate);
        Resources testResources = resourcesList.get(resourcesList.size() - 1);
        assertThat(testResources.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResources.getLinkUrl()).isEqualTo(UPDATED_LINK_URL);
        assertThat(testResources.getIconUrl()).isEqualTo(UPDATED_ICON_URL);
        assertThat(testResources.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResources.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResources.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the Resources in Elasticsearch
        verify(mockResourcesSearchRepository, times(1)).save(testResources);
    }

    @Test
    @Transactional
    public void updateNonExistingResources() throws Exception {
        int databaseSizeBeforeUpdate = resourcesRepository.findAll().size();

        // Create the Resources
        ResourcesDTO resourcesDTO = resourcesMapper.toDto(resources);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourcesMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourcesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resources in the database
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Resources in Elasticsearch
        verify(mockResourcesSearchRepository, times(0)).save(resources);
    }

    @Test
    @Transactional
    public void deleteResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);

        int databaseSizeBeforeDelete = resourcesRepository.findAll().size();

        // Get the resources
        restResourcesMockMvc.perform(delete("/api/resources/{id}", resources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resources> resourcesList = resourcesRepository.findAll();
        assertThat(resourcesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Resources in Elasticsearch
        verify(mockResourcesSearchRepository, times(1)).deleteById(resources.getId());
    }

    @Test
    @Transactional
    public void searchResources() throws Exception {
        // Initialize the database
        resourcesRepository.saveAndFlush(resources);
        when(mockResourcesSearchRepository.search(queryStringQuery("id:" + resources.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(resources), PageRequest.of(0, 1), 1));
        // Search the resources
        restResourcesMockMvc.perform(get("/api/_search/resources?query=id:" + resources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resources.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].linkUrl").value(hasItem(DEFAULT_LINK_URL)))
            .andExpect(jsonPath("$.[*].iconUrl").value(hasItem(DEFAULT_ICON_URL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resources.class);
        Resources resources1 = new Resources();
        resources1.setId(1L);
        Resources resources2 = new Resources();
        resources2.setId(resources1.getId());
        assertThat(resources1).isEqualTo(resources2);
        resources2.setId(2L);
        assertThat(resources1).isNotEqualTo(resources2);
        resources1.setId(null);
        assertThat(resources1).isNotEqualTo(resources2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourcesDTO.class);
        ResourcesDTO resourcesDTO1 = new ResourcesDTO();
        resourcesDTO1.setId(1L);
        ResourcesDTO resourcesDTO2 = new ResourcesDTO();
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
        resourcesDTO2.setId(resourcesDTO1.getId());
        assertThat(resourcesDTO1).isEqualTo(resourcesDTO2);
        resourcesDTO2.setId(2L);
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
        resourcesDTO1.setId(null);
        assertThat(resourcesDTO1).isNotEqualTo(resourcesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resourcesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resourcesMapper.fromId(null)).isNull();
    }
}
