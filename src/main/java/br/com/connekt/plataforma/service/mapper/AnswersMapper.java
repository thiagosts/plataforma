package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.AnswersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answers and its DTO AnswersDTO.
 */
@Mapper(componentModel = "spring", uses = {ResultsDetailsMapper.class, QuestionsMapper.class})
public interface AnswersMapper extends EntityMapper<AnswersDTO, Answers> {

    @Mapping(source = "resultsDetails.id", target = "resultsDetailsId")
    @Mapping(source = "questions.id", target = "questionsId")
    AnswersDTO toDto(Answers answers);

    @Mapping(source = "resultsDetailsId", target = "resultsDetails")
    @Mapping(source = "questionsId", target = "questions")
    Answers toEntity(AnswersDTO answersDTO);

    default Answers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answers answers = new Answers();
        answers.setId(id);
        return answers;
    }
}
