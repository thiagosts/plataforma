package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Benefits entity.
 */
public class BenefitsDTO implements Serializable {

    private Long id;

    private String name;

    private String icon;

    private Long opportunitiesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getOpportunitiesId() {
        return opportunitiesId;
    }

    public void setOpportunitiesId(Long opportunitiesId) {
        this.opportunitiesId = opportunitiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BenefitsDTO benefitsDTO = (BenefitsDTO) o;
        if (benefitsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), benefitsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BenefitsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", icon='" + getIcon() + "'" +
            ", opportunities=" + getOpportunitiesId() +
            "}";
    }
}
