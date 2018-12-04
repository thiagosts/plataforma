package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Answers.
 */
@Entity
@Table(name = "answers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "answers")
public class Answers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_value")
    private Integer value;

    @Column(name = "max_size")
    private Integer maxSize;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private ResultsDetails resultsDetails;

    @ManyToOne
    @JsonIgnoreProperties("answers")
    private Questions questions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Answers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public Answers value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public Answers maxSize(Integer maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public ResultsDetails getResultsDetails() {
        return resultsDetails;
    }

    public Answers resultsDetails(ResultsDetails resultsDetails) {
        this.resultsDetails = resultsDetails;
        return this;
    }

    public void setResultsDetails(ResultsDetails resultsDetails) {
        this.resultsDetails = resultsDetails;
    }

    public Questions getQuestions() {
        return questions;
    }

    public Answers questions(Questions questions) {
        this.questions = questions;
        return this;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answers answers = (Answers) o;
        if (answers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value=" + getValue() +
            ", maxSize=" + getMaxSize() +
            "}";
    }
}
