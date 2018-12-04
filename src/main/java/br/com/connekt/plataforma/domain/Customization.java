package br.com.connekt.plataforma.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Customization.
 */
@Entity
@Table(name = "customization")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customization")
public class Customization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "custom_css")
    private String customCSS;

    @Column(name = "url_logo")
    private String urlLogo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomCSS() {
        return customCSS;
    }

    public Customization customCSS(String customCSS) {
        this.customCSS = customCSS;
        return this;
    }

    public void setCustomCSS(String customCSS) {
        this.customCSS = customCSS;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public Customization urlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
        return this;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
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
        Customization customization = (Customization) o;
        if (customization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customization{" +
            "id=" + getId() +
            ", customCSS='" + getCustomCSS() + "'" +
            ", urlLogo='" + getUrlLogo() + "'" +
            "}";
    }
}
