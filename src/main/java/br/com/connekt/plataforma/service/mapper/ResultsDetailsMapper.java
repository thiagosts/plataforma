package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.ResultsDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ResultsDetails and its DTO ResultsDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {ResultsMapper.class})
public interface ResultsDetailsMapper extends EntityMapper<ResultsDetailsDTO, ResultsDetails> {

    @Mapping(source = "results.id", target = "resultsId")
    ResultsDetailsDTO toDto(ResultsDetails resultsDetails);

    @Mapping(target = "answers", ignore = true)
    @Mapping(source = "resultsId", target = "results")
    ResultsDetails toEntity(ResultsDetailsDTO resultsDetailsDTO);

    default ResultsDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResultsDetails resultsDetails = new ResultsDetails();
        resultsDetails.setId(id);
        return resultsDetails;
    }
}
