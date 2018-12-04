package br.com.connekt.plataforma.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Customers.
 */
@Entity
@Table(name = "customers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customers")
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "document_code")
    private String documentCode;

    @Column(name = "company_size")
    private String companySize;

    @Column(name = "description")
    private String description;

    @Column(name = "customers_code")
    private String customersCode;

    @Column(name = "logo_desktop_url")
    private String logoDesktopUrl;

    @Column(name = "logo_mobile_url")
    private String logoMobileUrl;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Portal> portals = new HashSet<>();
    @OneToMany(mappedBy = "customers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Opportunities> opportunities = new HashSet<>();
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

    public Customers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public Customers documentCode(String documentCode) {
        this.documentCode = documentCode;
        return this;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getCompanySize() {
        return companySize;
    }

    public Customers companySize(String companySize) {
        this.companySize = companySize;
        return this;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getDescription() {
        return description;
    }

    public Customers description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomersCode() {
        return customersCode;
    }

    public Customers customersCode(String customersCode) {
        this.customersCode = customersCode;
        return this;
    }

    public void setCustomersCode(String customersCode) {
        this.customersCode = customersCode;
    }

    public String getLogoDesktopUrl() {
        return logoDesktopUrl;
    }

    public Customers logoDesktopUrl(String logoDesktopUrl) {
        this.logoDesktopUrl = logoDesktopUrl;
        return this;
    }

    public void setLogoDesktopUrl(String logoDesktopUrl) {
        this.logoDesktopUrl = logoDesktopUrl;
    }

    public String getLogoMobileUrl() {
        return logoMobileUrl;
    }

    public Customers logoMobileUrl(String logoMobileUrl) {
        this.logoMobileUrl = logoMobileUrl;
        return this;
    }

    public void setLogoMobileUrl(String logoMobileUrl) {
        this.logoMobileUrl = logoMobileUrl;
    }

    public Boolean isActive() {
        return active;
    }

    public Customers active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Portal> getPortals() {
        return portals;
    }

    public Customers portals(Set<Portal> portals) {
        this.portals = portals;
        return this;
    }

    public Customers addPortal(Portal portal) {
        this.portals.add(portal);
        portal.setCustomers(this);
        return this;
    }

    public Customers removePortal(Portal portal) {
        this.portals.remove(portal);
        portal.setCustomers(null);
        return this;
    }

    public void setPortals(Set<Portal> portals) {
        this.portals = portals;
    }

    public Set<Opportunities> getOpportunities() {
        return opportunities;
    }

    public Customers opportunities(Set<Opportunities> opportunities) {
        this.opportunities = opportunities;
        return this;
    }

    public Customers addOpportunities(Opportunities opportunities) {
        this.opportunities.add(opportunities);
        opportunities.setCustomers(this);
        return this;
    }

    public Customers removeOpportunities(Opportunities opportunities) {
        this.opportunities.remove(opportunities);
        opportunities.setCustomers(null);
        return this;
    }

    public void setOpportunities(Set<Opportunities> opportunities) {
        this.opportunities = opportunities;
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
        Customers customers = (Customers) o;
        if (customers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Customers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", documentCode='" + getDocumentCode() + "'" +
            ", companySize='" + getCompanySize() + "'" +
            ", description='" + getDescription() + "'" +
            ", customersCode='" + getCustomersCode() + "'" +
            ", logoDesktopUrl='" + getLogoDesktopUrl() + "'" +
            ", logoMobileUrl='" + getLogoMobileUrl() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
