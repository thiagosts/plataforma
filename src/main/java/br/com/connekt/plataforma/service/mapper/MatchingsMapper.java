package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.MatchingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Matchings and its DTO MatchingsDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomizationMapper.class, MatchingsJobMapper.class})
public interface MatchingsMapper extends EntityMapper<MatchingsDTO, Matchings> {

    @Mapping(source = "customization.id", target = "customizationId")
    @Mapping(source = "matchingsjob.id", target = "matchingsjobId")
    MatchingsDTO toDto(Matchings matchings);

    @Mapping(source = "customizationId", target = "customization")
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "results", ignore = true)
    @Mapping(source = "matchingsjobId", target = "matchingsjob")
    Matchings toEntity(MatchingsDTO matchingsDTO);

    default Matchings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Matchings matchings = new Matchings();
        matchings.setId(id);
        return matchings;
    }
}
