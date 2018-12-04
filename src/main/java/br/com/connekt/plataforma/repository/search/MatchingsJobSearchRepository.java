package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.MatchingsJob;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MatchingsJob entity.
 */
public interface MatchingsJobSearchRepository extends ElasticsearchRepository<MatchingsJob, Long> {
}
