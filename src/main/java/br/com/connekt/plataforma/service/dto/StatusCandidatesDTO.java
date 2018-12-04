package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the StatusCandidates entity.
 */
public class StatusCandidatesDTO implements Serializable {

    private Long id;

    private String stageName;

    private String source;

    private String subStageName;

    private Long candidatesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubStageName() {
        return subStageName;
    }

    public void setSubStageName(String subStageName) {
        this.subStageName = subStageName;
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

        StatusCandidatesDTO statusCandidatesDTO = (StatusCandidatesDTO) o;
        if (statusCandidatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusCandidatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusCandidatesDTO{" +
            "id=" + getId() +
            ", stageName='" + getStageName() + "'" +
            ", source='" + getSource() + "'" +
            ", subStageName='" + getSubStageName() + "'" +
            ", candidates=" + getCandidatesId() +
            "}";
    }
}
