package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Answers entity.
 */
public class AnswersDTO implements Serializable {

    private Long id;

    private String name;

    private Integer value;

    private Integer maxSize;

    private Long resultsDetailsId;

    private Long questionsId;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Long getResultsDetailsId() {
        return resultsDetailsId;
    }

    public void setResultsDetailsId(Long resultsDetailsId) {
        this.resultsDetailsId = resultsDetailsId;
    }

    public Long getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Long questionsId) {
        this.questionsId = questionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswersDTO answersDTO = (AnswersDTO) o;
        if (answersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", maxSize=" + getMaxSize() +
            ", resultsDetails=" + getResultsDetailsId() +
            ", questions=" + getQuestionsId() +
            "}";
    }
}
