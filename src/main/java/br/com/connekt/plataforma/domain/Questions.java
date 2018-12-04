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
 * A Questions.
 */
@Entity
@Table(name = "questions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "questions")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_order")
    private Integer order;

    @Column(name = "jhi_require")
    private String require;

    @OneToMany(mappedBy = "questions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answers> answers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Matchings matchings;

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

    public Questions name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Questions description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Questions type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public Questions order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getRequire() {
        return require;
    }

    public Questions require(String require) {
        this.require = require;
        return this;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public Set<Answers> getAnswers() {
        return answers;
    }

    public Questions answers(Set<Answers> answers) {
        this.answers = answers;
        return this;
    }

    public Questions addAnswers(Answers answers) {
        this.answers.add(answers);
        answers.setQuestions(this);
        return this;
    }

    public Questions removeAnswers(Answers answers) {
        this.answers.remove(answers);
        answers.setQuestions(null);
        return this;
    }

    public void setAnswers(Set<Answers> answers) {
        this.answers = answers;
    }

    public Matchings getMatchings() {
        return matchings;
    }

    public Questions matchings(Matchings matchings) {
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
        Questions questions = (Questions) o;
        if (questions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            ", require='" + getRequire() + "'" +
            "}";
    }
}
