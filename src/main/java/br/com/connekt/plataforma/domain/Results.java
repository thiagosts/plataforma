package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Results.
 */
@Entity
@Table(name = "results")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "results")
public class Results implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private Float value;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "final_time")
    private Instant finalTime;

    @Column(name = "max_time")
    private Instant maxTime;

    @OneToMany(mappedBy = "results")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultsDetails> resultsdetails = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("results")
    private StatusCandidates statuscandidates;

    @ManyToOne
    @JsonIgnoreProperties("results")
    private Matchings matchings;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public Results value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Results startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getFinalTime() {
        return finalTime;
    }

    public Results finalTime(Instant finalTime) {
        this.finalTime = finalTime;
        return this;
    }

    public void setFinalTime(Instant finalTime) {
        this.finalTime = finalTime;
    }

    public Instant getMaxTime() {
        return maxTime;
    }

    public Results maxTime(Instant maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public void setMaxTime(Instant maxTime) {
        this.maxTime = maxTime;
    }

    public Set<ResultsDetails> getResultsdetails() {
        return resultsdetails;
    }

    public Results resultsdetails(Set<ResultsDetails> resultsDetails) {
        this.resultsdetails = resultsDetails;
        return this;
    }

    public Results addResultsdetails(ResultsDetails resultsDetails) {
        this.resultsdetails.add(resultsDetails);
        resultsDetails.setResults(this);
        return this;
    }

    public Results removeResultsdetails(ResultsDetails resultsDetails) {
        this.resultsdetails.remove(resultsDetails);
        resultsDetails.setResults(null);
        return this;
    }

    public void setResultsdetails(Set<ResultsDetails> resultsDetails) {
        this.resultsdetails = resultsDetails;
    }

    public StatusCandidates getStatuscandidates() {
        return statuscandidates;
    }

    public Results statuscandidates(StatusCandidates statusCandidates) {
        this.statuscandidates = statusCandidates;
        return this;
    }

    public void setStatuscandidates(StatusCandidates statusCandidates) {
        this.statuscandidates = statusCandidates;
    }

    public Matchings getMatchings() {
        return matchings;
    }

    public Results matchings(Matchings matchings) {
        this.matchings = matchings;
        return this;
    }

    public void setMatchings(Matchings matchings) {
        this.matchings = matchings;
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
        Results results = (Results) o;
        if (results.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), results.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Results{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", startTime='" + getStartTime() + "'" +
            ", finalTime='" + getFinalTime() + "'" +
            ", maxTime='" + getMaxTime() + "'" +
            "}";
    }
}
