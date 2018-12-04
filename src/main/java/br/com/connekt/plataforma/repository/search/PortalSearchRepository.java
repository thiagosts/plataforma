package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Portal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Portal entity.
 */
public interface PortalSearchRepository extends ElasticsearchRepository<Portal, Long> {
}
