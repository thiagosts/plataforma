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
 * A Matchings.
 */
@Entity
@Table(name = "matchings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "matchings")
public class Matchings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "jhi_time", precision = 10, scale = 2)
    private BigDecimal time;

    @Column(name = "is_default")
    private String isDefault;

    @OneToOne    @JoinColumn(unique = true)
    private Customization customization;

    @OneToMany(mappedBy = "matchings")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Questions> questions = new HashSet<>();
    @OneToMany(mappedBy = "matchings")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Results> results = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("matchings")
    private MatchingsJob matchingsjob;

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

    public Matchings name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Matchings type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Matchings createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Matchings lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public BigDecimal getTime() {
        return time;
    }

    public Matchings time(BigDecimal time) {
        this.time = time;
        return this;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public Matchings isDefault(String isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Customization getCustomization() {
        return customization;
    }

    public Matchings customization(Customization customization) {
        this.customization = customization;
        return this;
    }

    public void setCustomization(Customization customization) {
        this.customization = customization;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public Matchings questions(Set<Questions> questions) {
        this.questions = questions;
        return this;
    }

    public Matchings addQuestions(Questions questions) {
        this.questions.add(questions);
        questions.setMatchings(this);
        return this;
    }

    public Matchings removeQuestions(Questions questions) {
        this.questions.remove(questions);
        questions.setMatchings(null);
        return this;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }

    public Set<Results> getResults() {
        return results;
    }

    public Matchings results(Set<Results> results) {
        this.results = results;
        return this;
    }

    public Matchings addResults(Results results) {
        this.results.add(results);
        results.setMatchings(this);
        return this;
    }

    public Matchings removeResults(Results results) {
        this.results.remove(results);
        results.setMatchings(null);
        return this;
    }

    public void setResults(Set<Results> results) {
        this.results = results;
    }

    public MatchingsJob getMatchingsjob() {
        return matchingsjob;
    }

    public Matchings matchingsjob(MatchingsJob matchingsJob) {
        this.matchingsjob = matchingsJob;
        return this;
    }

    public void setMatchingsjob(MatchingsJob matchingsJob) {
        this.matchingsjob = matchingsJob;
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
        Matchings matchings = (Matchings) o;
        if (matchings.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchings.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Matchings{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", time=" + getTime() +
            ", isDefault='" + getIsDefault() + "'" +
            "}";
    }
}
