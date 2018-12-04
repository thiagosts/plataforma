package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.TemplatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Templates and its DTO TemplatesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemplatesMapper extends EntityMapper<TemplatesDTO, Templates> {



    default Templates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Templates templates = new Templates();
        templates.setId(id);
        return templates;
    }
}
