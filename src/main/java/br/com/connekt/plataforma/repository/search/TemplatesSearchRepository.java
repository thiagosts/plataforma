package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Templates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Templates entity.
 */
public interface TemplatesSearchRepository extends ElasticsearchRepository<Templates, Long> {
}
