package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Resources;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Resources entity.
 */
public interface ResourcesSearchRepository extends ElasticsearchRepository<Resources, Long> {
}
