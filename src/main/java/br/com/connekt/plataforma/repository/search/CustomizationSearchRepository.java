package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Customization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Customization entity.
 */
public interface CustomizationSearchRepository extends ElasticsearchRepository<Customization, Long> {
}
