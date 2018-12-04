package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Preferences;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Preferences entity.
 */
public interface PreferencesSearchRepository extends ElasticsearchRepository<Preferences, Long> {
}
