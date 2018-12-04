package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.ResourcesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resources and its DTO ResourcesDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplatesMapper.class})
public interface ResourcesMapper extends EntityMapper<ResourcesDTO, Resources> {

    @Mapping(source = "templates.id", target = "templatesId")
    ResourcesDTO toDto(Resources resources);

    @Mapping(source = "templatesId", target = "templates")
    Resources toEntity(ResourcesDTO resourcesDTO);

    default Resources fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resources resources = new Resources();
        resources.setId(id);
        return resources;
    }
}
