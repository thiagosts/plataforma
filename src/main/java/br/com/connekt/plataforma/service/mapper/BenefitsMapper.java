package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.BenefitsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Benefits and its DTO BenefitsDTO.
 */
@Mapper(componentModel = "spring", uses = {OpportunitiesMapper.class})
public interface BenefitsMapper extends EntityMapper<BenefitsDTO, Benefits> {

    @Mapping(source = "opportunities.id", target = "opportunitiesId")
    BenefitsDTO toDto(Benefits benefits);

    @Mapping(source = "opportunitiesId", target = "opportunities")
    Benefits toEntity(BenefitsDTO benefitsDTO);

    default Benefits fromId(Long id) {
        if (id == null) {
            return null;
        }
        Benefits benefits = new Benefits();
        benefits.setId(id);
        return benefits;
    }
}
