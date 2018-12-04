package br.com.connekt.plataforma.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Results entity.
 */
public class ResultsDTO implements Serializable {

    private Long id;

    private Float value;

    private Instant startTime;

    private Instant finalTime;

    private Instant maxTime;

    private Long statuscandidatesId;

    private Long matchingsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Instant finalTime) {
        this.finalTime = finalTime;
    }

    public Instant getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Instant maxTime) {
        this.maxTime = maxTime;
    }

    public Long getStatuscandidatesId() {
        return statuscandidatesId;
    }

    public void setStatuscandidatesId(Long statusCandidatesId) {
        this.statuscandidatesId = statusCandidatesId;
    }

    public Long getMatchingsId() {
        return matchingsId;
    }

    public void setMatchingsId(Long matchingsId) {
        this.matchingsId = matchingsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultsDTO resultsDTO = (ResultsDTO) o;
        if (resultsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultsDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", startTime='" + getStartTime() + "'" +
            ", finalTime='" + getFinalTime() + "'" +
            ", maxTime='" + getMaxTime() + "'" +
            ", statuscandidates=" + getStatuscandidatesId() +
            ", matchings=" + getMatchingsId() +
            "}";
    }
}
