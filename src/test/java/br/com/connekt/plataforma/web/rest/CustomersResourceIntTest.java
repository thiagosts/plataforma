package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Customers;
import br.com.connekt.plataforma.repository.CustomersRepository;
import br.com.connekt.plataforma.repository.search.CustomersSearchRepository;
import br.com.connekt.plataforma.service.CustomersService;
import br.com.connekt.plataforma.service.dto.CustomersDTO;
import br.com.connekt.plataforma.service.mapper.CustomersMapper;
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
 * Test class for the CustomersResource REST controller.
 *
 * @see CustomersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class CustomersResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMERS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMERS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_DESKTOP_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_DESKTOP_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_MOBILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_MOBILE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private CustomersService customersService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.CustomersSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomersSearchRepository mockCustomersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomersMockMvc;

    private Customers customers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomersResource customersResource = new CustomersResource(customersService);
        this.restCustomersMockMvc = MockMvcBuilders.standaloneSetup(customersResource)
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
    public static Customers createEntity(EntityManager em) {
        Customers customers = new Customers()
            .name(DEFAULT_NAME)
            .documentCode(DEFAULT_DOCUMENT_CODE)
            .companySize(DEFAULT_COMPANY_SIZE)
            .description(DEFAULT_DESCRIPTION)
            .customersCode(DEFAULT_CUSTOMERS_CODE)
            .logoDesktopUrl(DEFAULT_LOGO_DESKTOP_URL)
            .logoMobileUrl(DEFAULT_LOGO_MOBILE_URL)
            .active(DEFAULT_ACTIVE);
        return customers;
    }

    @Before
    public void initTest() {
        customers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomers() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);
        restCustomersMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isCreated());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate + 1);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomers.getDocumentCode()).isEqualTo(DEFAULT_DOCUMENT_CODE);
        assertThat(testCustomers.getCompanySize()).isEqualTo(DEFAULT_COMPANY_SIZE);
        assertThat(testCustomers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustomers.getCustomersCode()).isEqualTo(DEFAULT_CUSTOMERS_CODE);
        assertThat(testCustomers.getLogoDesktopUrl()).isEqualTo(DEFAULT_LOGO_DESKTOP_URL);
        assertThat(testCustomers.getLogoMobileUrl()).isEqualTo(DEFAULT_LOGO_MOBILE_URL);
        assertThat(testCustomers.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the Customers in Elasticsearch
        verify(mockCustomersSearchRepository, times(1)).save(testCustomers);
    }

    @Test
    @Transactional
    public void createCustomersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // Create the Customers with an existing ID
        customers.setId(1L);
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Customers in Elasticsearch
        verify(mockCustomersSearchRepository, times(0)).save(customers);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].customersCode").value(hasItem(DEFAULT_CUSTOMERS_CODE.toString())))
            .andExpect(jsonPath("$.[*].logoDesktopUrl").value(hasItem(DEFAULT_LOGO_DESKTOP_URL.toString())))
            .andExpect(jsonPath("$.[*].logoMobileUrl").value(hasItem(DEFAULT_LOGO_MOBILE_URL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.documentCode").value(DEFAULT_DOCUMENT_CODE.toString()))
            .andExpect(jsonPath("$.companySize").value(DEFAULT_COMPANY_SIZE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.customersCode").value(DEFAULT_CUSTOMERS_CODE.toString()))
            .andExpect(jsonPath("$.logoDesktopUrl").value(DEFAULT_LOGO_DESKTOP_URL.toString()))
            .andExpect(jsonPath("$.logoMobileUrl").value(DEFAULT_LOGO_MOBILE_URL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomers() throws Exception {
        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers
        Customers updatedCustomers = customersRepository.findById(customers.getId()).get();
        // Disconnect from session so that the updates on updatedCustomers are not directly saved in db
        em.detach(updatedCustomers);
        updatedCustomers
            .name(UPDATED_NAME)
            .documentCode(UPDATED_DOCUMENT_CODE)
            .companySize(UPDATED_COMPANY_SIZE)
            .description(UPDATED_DESCRIPTION)
            .customersCode(UPDATED_CUSTOMERS_CODE)
            .logoDesktopUrl(UPDATED_LOGO_DESKTOP_URL)
            .logoMobileUrl(UPDATED_LOGO_MOBILE_URL)
            .active(UPDATED_ACTIVE);
        CustomersDTO customersDTO = customersMapper.toDto(updatedCustomers);

        restCustomersMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomers.getDocumentCode()).isEqualTo(UPDATED_DOCUMENT_CODE);
        assertThat(testCustomers.getCompanySize()).isEqualTo(UPDATED_COMPANY_SIZE);
        assertThat(testCustomers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustomers.getCustomersCode()).isEqualTo(UPDATED_CUSTOMERS_CODE);
        assertThat(testCustomers.getLogoDesktopUrl()).isEqualTo(UPDATED_LOGO_DESKTOP_URL);
        assertThat(testCustomers.getLogoMobileUrl()).isEqualTo(UPDATED_LOGO_MOBILE_URL);
        assertThat(testCustomers.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the Customers in Elasticsearch
        verify(mockCustomersSearchRepository, times(1)).save(testCustomers);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Customers in Elasticsearch
        verify(mockCustomersSearchRepository, times(0)).save(customers);
    }

    @Test
    @Transactional
    public void deleteCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeDelete = customersRepository.findAll().size();

        // Get the customers
        restCustomersMockMvc.perform(delete("/api/customers/{id}", customers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Customers in Elasticsearch
        verify(mockCustomersSearchRepository, times(1)).deleteById(customers.getId());
    }

    @Test
    @Transactional
    public void searchCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        when(mockCustomersSearchRepository.search(queryStringQuery("id:" + customers.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(customers), PageRequest.of(0, 1), 1));
        // Search the customers
        restCustomersMockMvc.perform(get("/api/_search/customers?query=id:" + customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE)))
            .andExpect(jsonPath("$.[*].companySize").value(hasItem(DEFAULT_COMPANY_SIZE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].customersCode").value(hasItem(DEFAULT_CUSTOMERS_CODE)))
            .andExpect(jsonPath("$.[*].logoDesktopUrl").value(hasItem(DEFAULT_LOGO_DESKTOP_URL)))
            .andExpect(jsonPath("$.[*].logoMobileUrl").value(hasItem(DEFAULT_LOGO_MOBILE_URL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customers.class);
        Customers customers1 = new Customers();
        customers1.setId(1L);
        Customers customers2 = new Customers();
        customers2.setId(customers1.getId());
        assertThat(customers1).isEqualTo(customers2);
        customers2.setId(2L);
        assertThat(customers1).isNotEqualTo(customers2);
        customers1.setId(null);
        assertThat(customers1).isNotEqualTo(customers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersDTO.class);
        CustomersDTO customersDTO1 = new CustomersDTO();
        customersDTO1.setId(1L);
        CustomersDTO customersDTO2 = new CustomersDTO();
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
        customersDTO2.setId(customersDTO1.getId());
        assertThat(customersDTO1).isEqualTo(customersDTO2);
        customersDTO2.setId(2L);
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
        customersDTO1.setId(null);
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customersMapper.fromId(null)).isNull();
    }
}
