package br.com.connekt.plataforma.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of QuestionsSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QuestionsSearchRepositoryMockConfiguration {

    @MockBean
    private QuestionsSearchRepository mockQuestionsSearchRepository;

}
