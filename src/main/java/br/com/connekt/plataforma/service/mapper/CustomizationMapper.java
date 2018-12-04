package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.CustomizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customization and its DTO CustomizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomizationMapper extends EntityMapper<CustomizationDTO, Customization> {



    default Customization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customization customization = new Customization();
        customization.setId(id);
        return customization;
    }
}
