package br.com.connekt.plataforma.web.rest;

import br.com.connekt.plataforma.PlataformaApp;

import br.com.connekt.plataforma.domain.Questions;
import br.com.connekt.plataforma.repository.QuestionsRepository;
import br.com.connekt.plataforma.repository.search.QuestionsSearchRepository;
import br.com.connekt.plataforma.service.QuestionsService;
import br.com.connekt.plataforma.service.dto.QuestionsDTO;
import br.com.connekt.plataforma.service.mapper.QuestionsMapper;
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
 * Test class for the QuestionsResource REST controller.
 *
 * @see QuestionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlataformaApp.class)
public class QuestionsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_REQUIRE = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRE = "BBBBBBBBBB";

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private QuestionsService questionsService;

    /**
     * This repository is mocked in the br.com.connekt.plataforma.repository.search test package.
     *
     * @see br.com.connekt.plataforma.repository.search.QuestionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuestionsSearchRepository mockQuestionsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionsResource questionsResource = new QuestionsResource(questionsService);
        this.restQuestionsMockMvc = MockMvcBuilders.standaloneSetup(questionsResource)
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
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .order(DEFAULT_ORDER)
            .require(DEFAULT_REQUIRE);
        return questions;
    }

    @Before
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuestions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testQuestions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testQuestions.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testQuestions.getRequire()).isEqualTo(DEFAULT_REQUIRE);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).save(testQuestions);
    }

    @Test
    @Transactional
    public void createQuestionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions with an existing ID
        questions.setId(1L);
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(0)).save(questions);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].require").value(hasItem(DEFAULT_REQUIRE.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.require").value(DEFAULT_REQUIRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .order(UPDATED_ORDER)
            .require(UPDATED_REQUIRE);
        QuestionsDTO questionsDTO = questionsMapper.toDto(updatedQuestions);

        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuestions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testQuestions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testQuestions.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testQuestions.getRequire()).isEqualTo(UPDATED_REQUIRE);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).save(testQuestions);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(0)).save(questions);
    }

    @Test
    @Transactional
    public void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Get the questions
        restQuestionsMockMvc.perform(delete("/api/questions/{id}", questions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Questions in Elasticsearch
        verify(mockQuestionsSearchRepository, times(1)).deleteById(questions.getId());
    }

    @Test
    @Transactional
    public void searchQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);
        when(mockQuestionsSearchRepository.search(queryStringQuery("id:" + questions.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(questions), PageRequest.of(0, 1), 1));
        // Search the questions
        restQuestionsMockMvc.perform(get("/api/_search/questions?query=id:" + questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].require").value(hasItem(DEFAULT_REQUIRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questions.class);
        Questions questions1 = new Questions();
        questions1.setId(1L);
        Questions questions2 = new Questions();
        questions2.setId(questions1.getId());
        assertThat(questions1).isEqualTo(questions2);
        questions2.setId(2L);
        assertThat(questions1).isNotEqualTo(questions2);
        questions1.setId(null);
        assertThat(questions1).isNotEqualTo(questions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsDTO.class);
        QuestionsDTO questionsDTO1 = new QuestionsDTO();
        questionsDTO1.setId(1L);
        QuestionsDTO questionsDTO2 = new QuestionsDTO();
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO2.setId(questionsDTO1.getId());
        assertThat(questionsDTO1).isEqualTo(questionsDTO2);
        questionsDTO2.setId(2L);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO1.setId(null);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionsMapper.fromId(null)).isNull();
    }
}
