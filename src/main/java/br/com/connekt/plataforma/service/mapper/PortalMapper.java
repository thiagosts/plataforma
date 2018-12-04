package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.PortalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Portal and its DTO PortalDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplatesMapper.class, CustomersMapper.class})
public interface PortalMapper extends EntityMapper<PortalDTO, Portal> {

    @Mapping(source = "templates.id", target = "templatesId")
    @Mapping(source = "customers.id", target = "customersId")
    PortalDTO toDto(Portal portal);

    @Mapping(source = "templatesId", target = "templates")
    @Mapping(source = "customersId", target = "customers")
    Portal toEntity(PortalDTO portalDTO);

    default Portal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Portal portal = new Portal();
        portal.setId(id);
        return portal;
    }
}
