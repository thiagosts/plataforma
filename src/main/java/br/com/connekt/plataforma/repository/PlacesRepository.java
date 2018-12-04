package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Places;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Places entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlacesRepository extends JpaRepository<Places, Long> {

}
