package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Matchings;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Matchings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatchingsRepository extends JpaRepository<Matchings, Long> {

}
