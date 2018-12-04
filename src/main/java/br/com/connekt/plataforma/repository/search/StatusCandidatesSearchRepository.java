package br.com.connekt.plataforma.repository.search;

import br.com.connekt.plataforma.domain.StatusCandidates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the StatusCandidates entity.
 */
public interface StatusCandidatesSearchRepository extends ElasticsearchRepository<StatusCandidates, Long> {
}
