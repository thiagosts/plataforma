package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MatchingsJob entity.
 */
public class MatchingsJobDTO implements Serializable {

    private Long id;

    private Float cutNote;

    private Integer order;

    private String require;

    private Long opportunitiesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCutNote() {
        return cutNote;
    }

    public void setCutNote(Float cutNote) {
        this.cutNote = cutNote;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
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

        MatchingsJobDTO matchingsJobDTO = (MatchingsJobDTO) o;
        if (matchingsJobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchingsJobDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchingsJobDTO{" +
            "id=" + getId() +
            ", cutNote=" + getCutNote() +
            ", order=" + getOrder() +
            ", require='" + getRequire() + "'" +
            ", opportunities=" + getOpportunitiesId() +
            "}";
    }
}
