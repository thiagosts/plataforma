package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Places;
import br.com.connekt.plataforma.repository.PlacesRepository;
import br.com.connekt.plataforma.repository.search.PlacesSearchRepository;
import br.com.connekt.plataforma.service.PlacesService;
import br.com.connekt.plataforma.service.dto.PlacesDTO;
import br.com.connekt.plataforma.service.mapper.PlacesMapper;
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
 * Test class for the PlacesResource REST controller.
 *
 * @see PlacesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class PlacesResourceIntTest {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    @Autowired
    private PlacesRepository placesRepository;

    @Autowired
    private PlacesMapper placesMapper;

    @Autowired
    private PlacesService placesService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.PlacesSearchRepositoryMockConfiguration
     */
    @Autowired
    private PlacesSearchRepository mockPlacesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlacesMockMvc;

    private Places places;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlacesResource placesResource = new PlacesResource(placesService);
        this.restPlacesMockMvc = MockMvcBuilders.standaloneSetup(placesResource)
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
    public static Places createEntity(EntityManager em) {
        Places places = new Places()
            .address(DEFAULT_ADDRESS)
            .district(DEFAULT_DISTRICT)
            .city(DEFAULT_CITY)
            .zone(DEFAULT_ZONE)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .zipCode(DEFAULT_ZIP_CODE);
        return places;
    }

    @Before
    public void initTest() {
        places = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaces() throws Exception {
        int databaseSizeBeforeCreate = placesRepository.findAll().size();

        // Create the Places
        PlacesDTO placesDTO = placesMapper.toDto(places);
        restPlacesMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placesDTO)))
            .andExpect(status().isCreated());

        // Validate the Places in the database
        List<Places> placesList = placesRepository.findAll();
        assertThat(placesList).hasSize(databaseSizeBeforeCreate + 1);
        Places testPlaces = placesList.get(placesList.size() - 1);
        assertThat(testPlaces.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPlaces.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testPlaces.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPlaces.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testPlaces.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testPlaces.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPlaces.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);

        // Validate the Places in Elasticsearch
        verify(mockPlacesSearchRepository, times(1)).save(testPlaces);
    }

    @Test
    @Transactional
    public void createPlacesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = placesRepository.findAll().size();

        // Create the Places with an existing ID
        places.setId(1L);
        PlacesDTO placesDTO = placesMapper.toDto(places);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlacesMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Places in the database
        List<Places> placesList = placesRepository.findAll();
        assertThat(placesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Places in Elasticsearch
        verify(mockPlacesSearchRepository, times(0)).save(places);
    }

    @Test
    @Transactional
    public void getAllPlaces() throws Exception {
        // Initialize the database
        placesRepository.saveAndFlush(places);

        // Get all the placesList
        restPlacesMockMvc.perform(get("/api/places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(places.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getPlaces() throws Exception {
        // Initialize the database
        placesRepository.saveAndFlush(places);

        // Get the places
        restPlacesMockMvc.perform(get("/api/places/{id}", places.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(places.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaces() throws Exception {
        // Get the places
        restPlacesMockMvc.perform(get("/api/places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaces() throws Exception {
        // Initialize the database
        placesRepository.saveAndFlush(places);

        int databaseSizeBeforeUpdate = placesRepository.findAll().size();

        // Update the places
        Places updatedPlaces = placesRepository.findById(places.getId()).get();
        // Disconnect from session so that the updates on updatedPlaces are not directly saved in db
        em.detach(updatedPlaces);
        updatedPlaces
            .address(UPDATED_ADDRESS)
            .district(UPDATED_DISTRICT)
            .city(UPDATED_CITY)
            .zone(UPDATED_ZONE)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .zipCode(UPDATED_ZIP_CODE);
        PlacesDTO placesDTO = placesMapper.toDto(updatedPlaces);

        restPlacesMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placesDTO)))
            .andExpect(status().isOk());

        // Validate the Places in the database
        List<Places> placesList = placesRepository.findAll();
        assertThat(placesList).hasSize(databaseSizeBeforeUpdate);
        Places testPlaces = placesList.get(placesList.size() - 1);
        assertThat(testPlaces.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPlaces.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testPlaces.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPlaces.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testPlaces.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testPlaces.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlaces.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);

        // Validate the Places in Elasticsearch
        verify(mockPlacesSearchRepository, times(1)).save(testPlaces);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaces() throws Exception {
        int databaseSizeBeforeUpdate = placesRepository.findAll().size();

        // Create the Places
        PlacesDTO placesDTO = placesMapper.toDto(places);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlacesMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Places in the database
        List<Places> placesList = placesRepository.findAll();
        assertThat(placesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Places in Elasticsearch
        verify(mockPlacesSearchRepository, times(0)).save(places);
    }

    @Test
    @Transactional
    public void deletePlaces() throws Exception {
        // Initialize the database
        placesRepository.saveAndFlush(places);

        int databaseSizeBeforeDelete = placesRepository.findAll().size();

        // Get the places
        restPlacesMockMvc.perform(delete("/api/places/{id}", places.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Places> placesList = placesRepository.findAll();
        assertThat(placesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Places in Elasticsearch
        verify(mockPlacesSearchRepository, times(1)).deleteById(places.getId());
    }

    @Test
    @Transactional
    public void searchPlaces() throws Exception {
        // Initialize the database
        placesRepository.saveAndFlush(places);
        when(mockPlacesSearchRepository.search(queryStringQuery("id:" + places.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(places), PageRequest.of(0, 1), 1));
        // Search the places
        restPlacesMockMvc.perform(get("/api/_search/places?query=id:" + places.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(places.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Places.class);
        Places places1 = new Places();
        places1.setId(1L);
        Places places2 = new Places();
        places2.setId(places1.getId());
        assertThat(places1).isEqualTo(places2);
        places2.setId(2L);
        assertThat(places1).isNotEqualTo(places2);
        places1.setId(null);
        assertThat(places1).isNotEqualTo(places2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlacesDTO.class);
        PlacesDTO placesDTO1 = new PlacesDTO();
        placesDTO1.setId(1L);
        PlacesDTO placesDTO2 = new PlacesDTO();
        assertThat(placesDTO1).isNotEqualTo(placesDTO2);
        placesDTO2.setId(placesDTO1.getId());
        assertThat(placesDTO1).isEqualTo(placesDTO2);
        placesDTO2.setId(2L);
        assertThat(placesDTO1).isNotEqualTo(placesDTO2);
        placesDTO1.setId(null);
        assertThat(placesDTO1).isNotEqualTo(placesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(placesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(placesMapper.fromId(null)).isNull();
    }
}
