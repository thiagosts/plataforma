package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Preferences entity.
 */
public class PreferencesDTO implements Serializable {

    private Long id;

    private String area;

    private Long candidatesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCandidatesId() {
        return candidatesId;
    }

    public void setCandidatesId(Long candidatesId) {
        this.candidatesId = candidatesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreferencesDTO preferencesDTO = (PreferencesDTO) o;
        if (preferencesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preferencesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreferencesDTO{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", candidates=" + getCandidatesId() +
            "}";
    }
}
