package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Templates entity.
 */
public class TemplatesDTO implements Serializable {

    private Long id;

    private String name;

    private String customCss;

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

    public String getCustomCss() {
        return customCss;
    }

    public void setCustomCss(String customCss) {
        this.customCss = customCss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplatesDTO templatesDTO = (TemplatesDTO) o;
        if (templatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplatesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", customCss='" + getCustomCss() + "'" +
            "}";
    }
}
