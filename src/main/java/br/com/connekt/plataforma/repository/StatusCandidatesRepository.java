package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.StatusCandidates;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StatusCandidates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusCandidatesRepository extends JpaRepository<StatusCandidates, Long> {

}
