package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.Candidates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Candidates entity.
 */
public interface CandidatesSearchRepository extends ElasticsearchRepository<Candidates, Long> {
}
