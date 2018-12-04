package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Benefits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Benefits entity.
 */
public interface BenefitsSearchRepository extends ElasticsearchRepository<Benefits, Long> {
}
