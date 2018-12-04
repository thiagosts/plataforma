package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.QuestionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Questions and its DTO QuestionsDTO.
 */
@Mapper(componentModel = "spring", uses = {MatchingsMapper.class})
public interface QuestionsMapper extends EntityMapper<QuestionsDTO, Questions> {

    @Mapping(source = "matchings.id", target = "matchingsId")
    QuestionsDTO toDto(Questions questions);

    @Mapping(target = "answers", ignore = true)
    @Mapping(source = "matchingsId", target = "matchings")
    Questions toEntity(QuestionsDTO questionsDTO);

    default Questions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questions questions = new Questions();
        questions.setId(id);
        return questions;
    }
}
