package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Places;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Places entity.
 */
public interface PlacesSearchRepository extends ElasticsearchRepository<Places, Long> {
}
