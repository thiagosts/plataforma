package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Requests;
import br.com.connekt.plataforma.repository.RequestsRepository;
import br.com.connekt.plataforma.repository.search.RequestsSearchRepository;
import br.com.connekt.plataforma.service.RequestsService;
import br.com.connekt.plataforma.service.dto.RequestsDTO;
import br.com.connekt.plataforma.service.mapper.RequestsMapper;
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
 * Test class for the RequestsResource REST controller.
 *
 * @see RequestsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class RequestsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private RequestsMapper requestsMapper;

    @Autowired
    private RequestsService requestsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.RequestsSearchRepositoryMockConfiguration
     */
    @Autowired
    private RequestsSearchRepository mockRequestsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestsMockMvc;

    private Requests requests;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestsResource requestsResource = new RequestsResource(requestsService);
        this.restRequestsMockMvc = MockMvcBuilders.standaloneSetup(requestsResource)
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
    public static Requests createEntity(EntityManager em) {
        Requests requests = new Requests()
            .name(DEFAULT_NAME);
        return requests;
    }

    @Before
    public void initTest() {
        requests = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequests() throws Exception {
        int databaseSizeBeforeCreate = requestsRepository.findAll().size();

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);
        restRequestsMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isCreated());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeCreate + 1);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Requests in Elasticsearch
        verify(mockRequestsSearchRepository, times(1)).save(testRequests);
    }

    @Test
    @Transactional
    public void createRequestsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestsRepository.findAll().size();

        // Create the Requests with an existing ID
        requests.setId(1L);
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestsMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Requests in Elasticsearch
        verify(mockRequestsSearchRepository, times(0)).save(requests);
    }

    @Test
    @Transactional
    public void getAllRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        // Get all the requestsList
        restRequestsMockMvc.perform(get("/api/requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requests.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        // Get the requests
        restRequestsMockMvc.perform(get("/api/requests/{id}", requests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requests.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequests() throws Exception {
        // Get the requests
        restRequestsMockMvc.perform(get("/api/requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();

        // Update the requests
        Requests updatedRequests = requestsRepository.findById(requests.getId()).get();
        // Disconnect from session so that the updates on updatedRequests are not directly saved in db
        em.detach(updatedRequests);
        updatedRequests
            .name(UPDATED_NAME);
        RequestsDTO requestsDTO = requestsMapper.toDto(updatedRequests);

        restRequestsMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isOk());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);
        Requests testRequests = requestsList.get(requestsList.size() - 1);
        assertThat(testRequests.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Requests in Elasticsearch
        verify(mockRequestsSearchRepository, times(1)).save(testRequests);
    }

    @Test
    @Transactional
    public void updateNonExistingRequests() throws Exception {
        int databaseSizeBeforeUpdate = requestsRepository.findAll().size();

        // Create the Requests
        RequestsDTO requestsDTO = requestsMapper.toDto(requests);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestsMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Requests in the database
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Requests in Elasticsearch
        verify(mockRequestsSearchRepository, times(0)).save(requests);
    }

    @Test
    @Transactional
    public void deleteRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);

        int databaseSizeBeforeDelete = requestsRepository.findAll().size();

        // Get the requests
        restRequestsMockMvc.perform(delete("/api/requests/{id}", requests.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Requests> requestsList = requestsRepository.findAll();
        assertThat(requestsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Requests in Elasticsearch
        verify(mockRequestsSearchRepository, times(1)).deleteById(requests.getId());
    }

    @Test
    @Transactional
    public void searchRequests() throws Exception {
        // Initialize the database
        requestsRepository.saveAndFlush(requests);
        when(mockRequestsSearchRepository.search(queryStringQuery("id:" + requests.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(requests), PageRequest.of(0, 1), 1));
        // Search the requests
        restRequestsMockMvc.perform(get("/api/_search/requests?query=id:" + requests.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requests.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Requests.class);
        Requests requests1 = new Requests();
        requests1.setId(1L);
        Requests requests2 = new Requests();
        requests2.setId(requests1.getId());
        assertThat(requests1).isEqualTo(requests2);
        requests2.setId(2L);
        assertThat(requests1).isNotEqualTo(requests2);
        requests1.setId(null);
        assertThat(requests1).isNotEqualTo(requests2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestsDTO.class);
        RequestsDTO requestsDTO1 = new RequestsDTO();
        requestsDTO1.setId(1L);
        RequestsDTO requestsDTO2 = new RequestsDTO();
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
        requestsDTO2.setId(requestsDTO1.getId());
        assertThat(requestsDTO1).isEqualTo(requestsDTO2);
        requestsDTO2.setId(2L);
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
        requestsDTO1.setId(null);
        assertThat(requestsDTO1).isNotEqualTo(requestsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requestsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requestsMapper.fromId(null)).isNull();
    }
}
