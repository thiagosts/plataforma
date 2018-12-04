package br.com.connekt.plataforma.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Templates.
 */
@Entity
@Table(name = "templates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "templates")
public class Templates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "custom_css")
    private String customCss;

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

    public Templates name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomCss() {
        return customCss;
    }

    public Templates customCss(String customCss) {
        this.customCss = customCss;
        return this;
    }

    public void setCustomCss(String customCss) {
        this.customCss = customCss;
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
        Templates templates = (Templates) o;
        if (templates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Templates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", customCss='" + getCustomCss() + "'" +
            "}";
    }
}
