package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StatusCandidates.
 */
@Entity
@Table(name = "status_candidates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "statuscandidates")
public class StatusCandidates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stage_name")
    private String stageName;

    @Column(name = "source")
    private String source;

    @Column(name = "sub_stage_name")
    private String subStageName;

    @ManyToOne
    @JsonIgnoreProperties("statuscandidates")
    private Candidates candidates;

    @OneToMany(mappedBy = "statuscandidates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Results> results = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public StatusCandidates stageName(String stageName) {
        this.stageName = stageName;
        return this;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getSource() {
        return source;
    }

    public StatusCandidates source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubStageName() {
        return subStageName;
    }

    public StatusCandidates subStageName(String subStageName) {
        this.subStageName = subStageName;
        return this;
    }

    public void setSubStageName(String subStageName) {
        this.subStageName = subStageName;
    }

    public Candidates getCandidates() {
        return candidates;
    }

    public StatusCandidates candidates(Candidates candidates) {
        this.candidates = candidates;
        return this;
    }

    public void setCandidates(Candidates candidates) {
        this.candidates = candidates;
    }

    public Set<Results> getResults() {
        return results;
    }

    public StatusCandidates results(Set<Results> results) {
        this.results = results;
        return this;
    }

    public StatusCandidates addResults(Results results) {
        this.results.add(results);
        results.setStatuscandidates(this);
        return this;
    }

    public StatusCandidates removeResults(Results results) {
        this.results.remove(results);
        results.setStatuscandidates(null);
        return this;
    }

    public void setResults(Set<Results> results) {
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
        StatusCandidates statusCandidates = (StatusCandidates) o;
        if (statusCandidates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), statusCandidates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StatusCandidates{" +
            "id=" + getId() +
            ", stageName='" + getStageName() + "'" +
            ", source='" + getSource() + "'" +
            ", subStageName='" + getSubStageName() + "'" +
            "}";
    }
}
