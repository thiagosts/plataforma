package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Opportunities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Opportunities entity.
 */
public interface OpportunitiesSearchRepository extends ElasticsearchRepository<Opportunities, Long> {
}
