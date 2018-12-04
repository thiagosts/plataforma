package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Opportunities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Opportunities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunities, Long> {

}
