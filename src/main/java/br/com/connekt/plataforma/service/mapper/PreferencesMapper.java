package br.com.connekt.plataforma.service.mapper;

import br.com.connekt.plataforma.domain.*;
import br.com.connekt.plataforma.service.dto.PreferencesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Preferences and its DTO PreferencesDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidatesMapper.class})
public interface PreferencesMapper extends EntityMapper<PreferencesDTO, Preferences> {

    @Mapping(source = "candidates.id", target = "candidatesId")
    PreferencesDTO toDto(Preferences preferences);

    @Mapping(source = "candidatesId", target = "candidates")
    Preferences toEntity(PreferencesDTO preferencesDTO);

    default Preferences fromId(Long id) {
        if (id == null) {
            return null;
        }
        Preferences preferences = new Preferences();
        preferences.setId(id);
        return preferences;
    }
}
