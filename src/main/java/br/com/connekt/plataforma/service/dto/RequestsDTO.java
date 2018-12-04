package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Requests entity.
 */
public class RequestsDTO implements Serializable {

    private Long id;

    private String name;

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

        RequestsDTO requestsDTO = (RequestsDTO) o;
        if (requestsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", opportunities=" + getOpportunitiesId() +
            "}";
    }
}
