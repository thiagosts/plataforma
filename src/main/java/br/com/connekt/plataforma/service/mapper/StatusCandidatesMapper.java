package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.StatusCandidatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StatusCandidates and its DTO StatusCandidatesDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidatesMapper.class})
public interface StatusCandidatesMapper extends EntityMapper<StatusCandidatesDTO, StatusCandidates> {

    @Mapping(source = "candidates.id", target = "candidatesId")
    StatusCandidatesDTO toDto(StatusCandidates statusCandidates);

    @Mapping(source = "candidatesId", target = "candidates")
    @Mapping(target = "results", ignore = true)
    StatusCandidates toEntity(StatusCandidatesDTO statusCandidatesDTO);

    default StatusCandidates fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatusCandidates statusCandidates = new StatusCandidates();
        statusCandidates.setId(id);
        return statusCandidates;
    }
}
