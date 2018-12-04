package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Resources;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Resources entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {

}
