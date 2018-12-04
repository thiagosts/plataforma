package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Portal;
import br.com.connekt.plataforma.repository.PortalRepository;
import br.com.connekt.plataforma.repository.search.PortalSearchRepository;
import br.com.connekt.plataforma.service.PortalService;
import br.com.connekt.plataforma.service.dto.PortalDTO;
import br.com.connekt.plataforma.service.mapper.PortalMapper;
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
 * Test class for the PortalResource REST controller.
 *
 * @see PortalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class PortalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private PortalRepository portalRepository;

    @Autowired
    private PortalMapper portalMapper;

    @Autowired
    private PortalService portalService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.PortalSearchRepositoryMockConfiguration
     */
    @Autowired
    private PortalSearchRepository mockPortalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortalMockMvc;

    private Portal portal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortalResource portalResource = new PortalResource(portalService);
        this.restPortalMockMvc = MockMvcBuilders.standaloneSetup(portalResource)
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
    public static Portal createEntity(EntityManager em) {
        Portal portal = new Portal()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return portal;
    }

    @Before
    public void initTest() {
        portal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortal() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal
        PortalDTO portalDTO = portalMapper.toDto(portal);
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalDTO)))
            .andExpect(status().isCreated());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate + 1);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPortal.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).save(testPortal);
    }

    @Test
    @Transactional
    public void createPortalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portalRepository.findAll().size();

        // Create the Portal with an existing ID
        portal.setId(1L);
        PortalDTO portalDTO = portalMapper.toDto(portal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortalMockMvc.perform(post("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(0)).save(portal);
    }

    @Test
    @Transactional
    public void getAllPortals() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get all the portalList
        restPortalMockMvc.perform(get("/api/portals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getPortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", portal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPortal() throws Exception {
        // Get the portal
        restPortalMockMvc.perform(get("/api/portals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Update the portal
        Portal updatedPortal = portalRepository.findById(portal.getId()).get();
        // Disconnect from session so that the updates on updatedPortal are not directly saved in db
        em.detach(updatedPortal);
        updatedPortal
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        PortalDTO portalDTO = portalMapper.toDto(updatedPortal);

        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalDTO)))
            .andExpect(status().isOk());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);
        Portal testPortal = portalList.get(portalList.size() - 1);
        assertThat(testPortal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPortal.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).save(testPortal);
    }

    @Test
    @Transactional
    public void updateNonExistingPortal() throws Exception {
        int databaseSizeBeforeUpdate = portalRepository.findAll().size();

        // Create the Portal
        PortalDTO portalDTO = portalMapper.toDto(portal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortalMockMvc.perform(put("/api/portals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Portal in the database
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(0)).save(portal);
    }

    @Test
    @Transactional
    public void deletePortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);

        int databaseSizeBeforeDelete = portalRepository.findAll().size();

        // Get the portal
        restPortalMockMvc.perform(delete("/api/portals/{id}", portal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Portal> portalList = portalRepository.findAll();
        assertThat(portalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Portal in Elasticsearch
        verify(mockPortalSearchRepository, times(1)).deleteById(portal.getId());
    }

    @Test
    @Transactional
    public void searchPortal() throws Exception {
        // Initialize the database
        portalRepository.saveAndFlush(portal);
        when(mockPortalSearchRepository.search(queryStringQuery("id:" + portal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(portal), PageRequest.of(0, 1), 1));
        // Search the portal
        restPortalMockMvc.perform(get("/api/_search/portals?query=id:" + portal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Portal.class);
        Portal portal1 = new Portal();
        portal1.setId(1L);
        Portal portal2 = new Portal();
        portal2.setId(portal1.getId());
        assertThat(portal1).isEqualTo(portal2);
        portal2.setId(2L);
        assertThat(portal1).isNotEqualTo(portal2);
        portal1.setId(null);
        assertThat(portal1).isNotEqualTo(portal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortalDTO.class);
        PortalDTO portalDTO1 = new PortalDTO();
        portalDTO1.setId(1L);
        PortalDTO portalDTO2 = new PortalDTO();
        assertThat(portalDTO1).isNotEqualTo(portalDTO2);
        portalDTO2.setId(portalDTO1.getId());
        assertThat(portalDTO1).isEqualTo(portalDTO2);
        portalDTO2.setId(2L);
        assertThat(portalDTO1).isNotEqualTo(portalDTO2);
        portalDTO1.setId(null);
        assertThat(portalDTO1).isNotEqualTo(portalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(portalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(portalMapper.fromId(null)).isNull();
    }
}
