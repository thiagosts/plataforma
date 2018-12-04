package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Customization entity.
 */
public class CustomizationDTO implements Serializable {

    private Long id;

    private String customCSS;

    private String urlLogo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomCSS() {
        return customCSS;
    }

    public void setCustomCSS(String customCSS) {
        this.customCSS = customCSS;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomizationDTO customizationDTO = (CustomizationDTO) o;
        if (customizationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customizationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomizationDTO{" +
            "id=" + getId() +
            ", customCSS='" + getCustomCSS() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            "}";
    }
}
