package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.PlacesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Places and its DTO PlacesDTO.
 */
@Mapper(componentModel = "spring", uses = {OpportunitiesMapper.class})
public interface PlacesMapper extends EntityMapper<PlacesDTO, Places> {

    @Mapping(source = "opportunities.id", target = "opportunitiesId")
    PlacesDTO toDto(Places places);

    @Mapping(source = "opportunitiesId", target = "opportunities")
    Places toEntity(PlacesDTO placesDTO);

    default Places fromId(Long id) {
        if (id == null) {
            return null;
        }
        Places places = new Places();
        places.setId(id);
        return places;
    }
}
