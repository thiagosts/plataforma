package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Answers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Answers entity.
 */
public interface AnswersSearchRepository extends ElasticsearchRepository<Answers, Long> {
}
