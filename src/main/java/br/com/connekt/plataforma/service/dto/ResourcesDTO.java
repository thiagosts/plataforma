package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Resources entity.
 */
public class ResourcesDTO implements Serializable {

    private Long id;

    private String name;

    private String linkUrl;

    private String iconUrl;

    private String type;

    private String title;

    private String description;

    private Long templatesId;

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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTemplatesId() {
        return templatesId;
    }

    public void setTemplatesId(Long templatesId) {
        this.templatesId = templatesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourcesDTO resourcesDTO = (ResourcesDTO) o;
        if (resourcesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourcesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourcesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", linkUrl='" + getLinkUrl() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", templates=" + getTemplatesId() +
            "}";
    }
}
