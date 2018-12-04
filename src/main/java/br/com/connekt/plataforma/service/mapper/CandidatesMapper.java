package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.CandidatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Candidates and its DTO CandidatesDTO.
 */
@Mapper(componentModel = "spring", uses = {PlacesMapper.class})
public interface CandidatesMapper extends EntityMapper<CandidatesDTO, Candidates> {

    @Mapping(source = "places.id", target = "placesId")
    CandidatesDTO toDto(Candidates candidates);

    @Mapping(source = "placesId", target = "places")
    @Mapping(target = "statuscandidates", ignore = true)
    @Mapping(target = "preferences", ignore = true)
    Candidates toEntity(CandidatesDTO candidatesDTO);

    default Candidates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidates candidates = new Candidates();
        candidates.setId(id);
        return candidates;
    }
}
