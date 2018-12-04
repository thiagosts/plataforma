package br.com.connekt.plataforma.repository;

import br.com.connekt.plataforma.domain.Requests;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Requests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {

}
