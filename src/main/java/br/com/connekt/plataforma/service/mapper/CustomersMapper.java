package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.CustomersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customers and its DTO CustomersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {


    @Mapping(target = "portals", ignore = true)
    @Mapping(target = "opportunities", ignore = true)
    Customers toEntity(CustomersDTO customersDTO);

    default Customers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customers customers = new Customers();
        customers.setId(id);
        return customers;
    }
}
