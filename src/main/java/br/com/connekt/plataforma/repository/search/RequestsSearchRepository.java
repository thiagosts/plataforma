package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Requests;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Requests entity.
 */
public interface RequestsSearchRepository extends ElasticsearchRepository<Requests, Long> {
}
