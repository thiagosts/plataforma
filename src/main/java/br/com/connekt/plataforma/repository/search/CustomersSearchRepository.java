package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Customers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Customers entity.
 */
public interface CustomersSearchRepository extends ElasticsearchRepository<Customers, Long> {
}
