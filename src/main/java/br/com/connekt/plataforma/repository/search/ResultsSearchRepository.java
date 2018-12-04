package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Results;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Results entity.
 */
public interface ResultsSearchRepository extends ElasticsearchRepository<Results, Long> {
}
