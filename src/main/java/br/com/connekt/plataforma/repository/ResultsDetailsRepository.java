package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.ResultsDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResultsDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultsDetailsRepository extends JpaRepository<ResultsDetails, Long> {

}
