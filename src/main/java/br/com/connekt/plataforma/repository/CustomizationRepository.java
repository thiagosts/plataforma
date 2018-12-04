package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Customization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Customization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomizationRepository extends JpaRepository<Customization, Long> {

}
