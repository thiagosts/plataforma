package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Questions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Questions entity.
 */
public interface QuestionsSearchRepository extends ElasticsearchRepository<Questions, Long> {
}
