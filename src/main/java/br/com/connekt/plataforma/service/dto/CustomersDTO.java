package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Customers entity.
 */
public class CustomersDTO implements Serializable {

    private Long id;

    private String name;

    private String documentCode;

    private String companySize;

    private String description;

    private String customersCode;

    private String logoDesktopUrl;

    private String logoMobileUrl;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomersCode() {
        return customersCode;
    }

    public void setCustomersCode(String customersCode) {
        this.customersCode = customersCode;
    }

    public String getLogoDesktopUrl() {
        return logoDesktopUrl;
    }

    public void setLogoDesktopUrl(String logoDesktopUrl) {
        this.logoDesktopUrl = logoDesktopUrl;
    }

    public String getLogoMobileUrl() {
        return logoMobileUrl;
    }

    public void setLogoMobileUrl(String logoMobileUrl) {
        this.logoMobileUrl = logoMobileUrl;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomersDTO customersDTO = (CustomersDTO) o;
        if (customersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomersDTO{" +
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
