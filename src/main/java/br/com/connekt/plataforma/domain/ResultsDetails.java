package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ResultsDetails.
 */
@Entity
@Table(name = "results_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resultsdetails")
public class ResultsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value", precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "result")
    private String result;

    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "resultsDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answers> answers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("resultsdetails")
    private Results results;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ResultsDetails value(BigDecimal value) {
        this.value = value;
        return this;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getResult() {
        return result;
    }

    public ResultsDetails result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ResultsDetails createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Answers> getAnswers() {
        return answers;
    }

    public ResultsDetails answers(Set<Answers> answers) {
        this.answers = answers;
        return this;
    }

    public ResultsDetails addAnswers(Answers answers) {
        this.answers.add(answers);
        answers.setResultsDetails(this);
        return this;
    }

    public ResultsDetails removeAnswers(Answers answers) {
        this.answers.remove(answers);
        answers.setResultsDetails(null);
        return this;
    }

    public void setAnswers(Set<Answers> answers) {
        this.answers = answers;
    }

    public Results getResults() {
        return results;
    }

    public ResultsDetails results(Results results) {
        this.results = results;
        return this;
    }

    public void setResults(Results results) {
        this.results = results;
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
        ResultsDetails resultsDetails = (ResultsDetails) o;
        if (resultsDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultsDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultsDetails{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", result='" + getResult() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
