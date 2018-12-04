package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.MatchingsJobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MatchingsJob and its DTO MatchingsJobDTO.
 */
@Mapper(componentModel = "spring", uses = {OpportunitiesMapper.class})
public interface MatchingsJobMapper extends EntityMapper<MatchingsJobDTO, MatchingsJob> {

    @Mapping(source = "opportunities.id", target = "opportunitiesId")
    MatchingsJobDTO toDto(MatchingsJob matchingsJob);

    @Mapping(source = "opportunitiesId", target = "opportunities")
    @Mapping(target = "matchings", ignore = true)
    MatchingsJob toEntity(MatchingsJobDTO matchingsJobDTO);

    default MatchingsJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        MatchingsJob matchingsJob = new MatchingsJob();
        matchingsJob.setId(id);
        return matchingsJob;
    }
}
