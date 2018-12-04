package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.ResultsDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ResultsDetails entity.
 */
public interface ResultsDetailsSearchRepository extends ElasticsearchRepository<ResultsDetails, Long> {
}
