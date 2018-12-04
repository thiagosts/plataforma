package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Portal entity.
 */
public class PortalDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Long templatesId;

    private Long customersId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTemplatesId() {
        return templatesId;
    }

    public void setTemplatesId(Long templatesId) {
        this.templatesId = templatesId;
    }

    public Long getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Long customersId) {
        this.customersId = customersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PortalDTO portalDTO = (PortalDTO) o;
        if (portalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PortalDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", templates=" + getTemplatesId() +
            ", customers=" + getCustomersId() +
            "}";
    }
}
