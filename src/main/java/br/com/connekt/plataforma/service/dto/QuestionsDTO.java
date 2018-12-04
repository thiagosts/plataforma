package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Questions entity.
 */
public class QuestionsDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String type;

    private Integer order;

    private String require;

    private Long matchingsId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        QuestionsDTO questionsDTO = (QuestionsDTO) o;
        if (questionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            ", require='" + getRequire() + "'" +
            ", matchings=" + getMatchingsId() +
            "}";
    }
}
