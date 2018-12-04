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

import br.com.connekt.plataforma.domain.enumeration.OpportunitiesTypeEnums;

/**
 * A Opportunities.
 */
@Entity
@Table(name = "opportunities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "opportunities")
public class Opportunities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opportunity_code")
    private String opportunityCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "opportunities_type")
    private OpportunitiesTypeEnums opportunitiesType;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "area")
    private String area;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "highlighted")
    private Boolean highlighted;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "logo_desktop_url")
    private String logoDesktopUrl;

    @Column(name = "logo_mobile_url")
    private String logoMobileUrl;

    @Column(name = "hiring_type")
    private String hiringType;

    @Column(name = "slug")
    private String slug;

    @OneToMany(mappedBy = "opportunities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Places> places = new HashSet<>();
    @OneToMany(mappedBy = "opportunities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Benefits> benefits = new HashSet<>();
    @OneToMany(mappedBy = "opportunities")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Requests> requests = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("opportunities")
    private Customers customers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpportunityCode() {
        return opportunityCode;
    }

    public Opportunities opportunityCode(String opportunityCode) {
        this.opportunityCode = opportunityCode;
        return this;
    }

    public void setOpportunityCode(String opportunityCode) {
        this.opportunityCode = opportunityCode;
    }

    public OpportunitiesTypeEnums getOpportunitiesType() {
        return opportunitiesType;
    }

    public Opportunities opportunitiesType(OpportunitiesTypeEnums opportunitiesType) {
        this.opportunitiesType = opportunitiesType;
        return this;
    }

    public void setOpportunitiesType(OpportunitiesTypeEnums opportunitiesType) {
        this.opportunitiesType = opportunitiesType;
    }

    public String getName() {
        return name;
    }

    public Opportunities name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public Opportunities status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public Opportunities area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExternalId() {
        return externalId;
    }

    public Opportunities externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Boolean isHighlighted() {
        return highlighted;
    }

    public Opportunities highlighted(Boolean highlighted) {
        this.highlighted = highlighted;
        return this;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public String getDescription() {
        return description;
    }

    public Opportunities description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Opportunities startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Opportunities endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Opportunities quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLogoDesktopUrl() {
        return logoDesktopUrl;
    }

    public Opportunities logoDesktopUrl(String logoDesktopUrl) {
        this.logoDesktopUrl = logoDesktopUrl;
        return this;
    }

    public void setLogoDesktopUrl(String logoDesktopUrl) {
        this.logoDesktopUrl = logoDesktopUrl;
    }

    public String getLogoMobileUrl() {
        return logoMobileUrl;
    }

    public Opportunities logoMobileUrl(String logoMobileUrl) {
        this.logoMobileUrl = logoMobileUrl;
        return this;
    }

    public void setLogoMobileUrl(String logoMobileUrl) {
        this.logoMobileUrl = logoMobileUrl;
    }

    public String getHiringType() {
        return hiringType;
    }

    public Opportunities hiringType(String hiringType) {
        this.hiringType = hiringType;
        return this;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public String getSlug() {
        return slug;
    }

    public Opportunities slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Set<Places> getPlaces() {
        return places;
    }

    public Opportunities places(Set<Places> places) {
        this.places = places;
        return this;
    }

    public Opportunities addPlaces(Places places) {
        this.places.add(places);
        places.setOpportunities(this);
        return this;
    }

    public Opportunities removePlaces(Places places) {
        this.places.remove(places);
        places.setOpportunities(null);
        return this;
    }

    public void setPlaces(Set<Places> places) {
        this.places = places;
    }

    public Set<Benefits> getBenefits() {
        return benefits;
    }

    public Opportunities benefits(Set<Benefits> benefits) {
        this.benefits = benefits;
        return this;
    }

    public Opportunities addBenefits(Benefits benefits) {
        this.benefits.add(benefits);
        benefits.setOpportunities(this);
        return this;
    }

    public Opportunities removeBenefits(Benefits benefits) {
        this.benefits.remove(benefits);
        benefits.setOpportunities(null);
        return this;
    }

    public void setBenefits(Set<Benefits> benefits) {
        this.benefits = benefits;
    }

    public Set<Requests> getRequests() {
        return requests;
    }

    public Opportunities requests(Set<Requests> requests) {
        this.requests = requests;
        return this;
    }

    public Opportunities addRequests(Requests requests) {
        this.requests.add(requests);
        requests.setOpportunities(this);
        return this;
    }

    public Opportunities removeRequests(Requests requests) {
        this.requests.remove(requests);
        requests.setOpportunities(null);
        return this;
    }

    public void setRequests(Set<Requests> requests) {
        this.requests = requests;
    }

    public Customers getCustomers() {
        return customers;
    }

    public Opportunities customers(Customers customers) {
        this.customers = customers;
        return this;
    }

    public void setCustomers(Customers customers) {
        this.customers = customers;
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
        Opportunities opportunities = (Opportunities) o;
        if (opportunities.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), opportunities.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Opportunities{" +
            "id=" + getId() +
            ", opportunityCode='" + getOpportunityCode() + "'" +
            ", opportunitiesType='" + getOpportunitiesType() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", area='" + getArea() + "'" +
            ", externalId='" + getExternalId() + "'" +
            ", highlighted='" + isHighlighted() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", quantity=" + getQuantity() +
            ", logoDesktopUrl='" + getLogoDesktopUrl() + "'" +
            ", logoMobileUrl='" + getLogoMobileUrl() + "'" +
            ", hiringType='" + getHiringType() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
