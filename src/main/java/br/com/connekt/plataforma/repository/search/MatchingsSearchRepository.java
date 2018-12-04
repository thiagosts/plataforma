package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Matchings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Matchings entity.
 */
public interface MatchingsSearchRepository extends ElasticsearchRepository<Matchings, Long> {
}
