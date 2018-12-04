package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Candidates.
 */
@Entity
@Table(name = "candidates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "candidates")
public class Candidates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "cel_phone")
    private String celPhone;

    @Column(name = "area")
    private String area;

    @Column(name = "data_of_birth")
    private Instant dataOfBirth;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "sales_force_id")
    private String salesForceId;

    @OneToOne    @JoinColumn(unique = true)
    private Places places;

    @OneToMany(mappedBy = "candidates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<StatusCandidates> statuscandidates = new HashSet<>();
    @OneToMany(mappedBy = "candidates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Preferences> preferences = new HashSet<>();
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

    public Candidates name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Candidates email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public Candidates celPhone(String celPhone) {
        this.celPhone = celPhone;
        return this;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public String getArea() {
        return area;
    }

    public Candidates area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Instant getDataOfBirth() {
        return dataOfBirth;
    }

    public Candidates dataOfBirth(Instant dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
        return this;
    }

    public void setDataOfBirth(Instant dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public Candidates occupation(String occupation) {
        this.occupation = occupation;
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Candidates pictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSalesForceId() {
        return salesForceId;
    }

    public Candidates salesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
        return this;
    }

    public void setSalesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
    }

    public Places getPlaces() {
        return places;
    }

    public Candidates places(Places places) {
        this.places = places;
        return this;
    }

    public void setPlaces(Places places) {
        this.places = places;
    }

    public Set<StatusCandidates> getStatuscandidates() {
        return statuscandidates;
    }

    public Candidates statuscandidates(Set<StatusCandidates> statusCandidates) {
        this.statuscandidates = statusCandidates;
        return this;
    }

    public Candidates addStatuscandidates(StatusCandidates statusCandidates) {
        this.statuscandidates.add(statusCandidates);
        statusCandidates.setCandidates(this);
        return this;
    }

    public Candidates removeStatuscandidates(StatusCandidates statusCandidates) {
        this.statuscandidates.remove(statusCandidates);
        statusCandidates.setCandidates(null);
        return this;
    }

    public void setStatuscandidates(Set<StatusCandidates> statusCandidates) {
        this.statuscandidates = statusCandidates;
    }

    public Set<Preferences> getPreferences() {
        return preferences;
    }

    public Candidates preferences(Set<Preferences> preferences) {
        this.preferences = preferences;
        return this;
    }

    public Candidates addPreferences(Preferences preferences) {
        this.preferences.add(preferences);
        preferences.setCandidates(this);
        return this;
    }

    public Candidates removePreferences(Preferences preferences) {
        this.preferences.remove(preferences);
        preferences.setCandidates(null);
        return this;
    }

    public void setPreferences(Set<Preferences> preferences) {
        this.preferences = preferences;
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
        Candidates candidates = (Candidates) o;
        if (candidates.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidates.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", celPhone='" + getCelPhone() + "'" +
            ", area='" + getArea() + "'" +
            ", dataOfBirth='" + getDataOfBirth() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", pictureUrl='" + getPictureUrl() + "'" +
            ", salesForceId='" + getSalesForceId() + "'" +
            "}";
    }
}
