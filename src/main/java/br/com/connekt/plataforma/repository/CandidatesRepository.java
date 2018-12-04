package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Candidates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Candidates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidatesRepository extends JpaRepository<Candidates, Long> {

}
