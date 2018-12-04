package br.com.connekt.plataforma.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the ResultsDetails entity.
 */
public class ResultsDetailsDTO implements Serializable {

    private Long id;

    private BigDecimal value;

    private String result;

    private Instant createdDate;

    private Long resultsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Long getResultsId() {
        return resultsId;
    }

    public void setResultsId(Long resultsId) {
        this.resultsId = resultsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultsDetailsDTO resultsDetailsDTO = (ResultsDetailsDTO) o;
        if (resultsDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultsDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultsDetailsDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", result='" + getResult() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", results=" + getResultsId() +
            "}";
    }
}
