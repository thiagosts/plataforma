package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.ResultsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Results and its DTO ResultsDTO.
 */
@Mapper(componentModel = "spring", uses = {StatusCandidatesMapper.class, MatchingsMapper.class})
public interface ResultsMapper extends EntityMapper<ResultsDTO, Results> {

    @Mapping(source = "statuscandidates.id", target = "statuscandidatesId")
    @Mapping(source = "matchings.id", target = "matchingsId")
    ResultsDTO toDto(Results results);

    @Mapping(target = "resultsdetails", ignore = true)
    @Mapping(source = "statuscandidatesId", target = "statuscandidates")
    @Mapping(source = "matchingsId", target = "matchings")
    Results toEntity(ResultsDTO resultsDTO);

    default Results fromId(Long id) {
        if (id == null) {
            return null;
        }
        Results results = new Results();
        results.setId(id);
        return results;
    }
}
