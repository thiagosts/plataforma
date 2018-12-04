package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.OpportunitiesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Opportunities and its DTO OpportunitiesDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomersMapper.class})
public interface OpportunitiesMapper extends EntityMapper<OpportunitiesDTO, Opportunities> {

    @Mapping(source = "customers.id", target = "customersId")
    OpportunitiesDTO toDto(Opportunities opportunities);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "benefits", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(source = "customersId", target = "customers")
    Opportunities toEntity(OpportunitiesDTO opportunitiesDTO);

    default Opportunities fromId(Long id) {
        if (id == null) {
            return null;
        }
        Opportunities opportunities = new Opportunities();
        opportunities.setId(id);
        return opportunities;
    }
}
