package br.com.connekt.plataforma.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import br.com.connekt.plataforma.domain.enumeration.OpportunitiesTypeEnums;

/**
 * A DTO for the Opportunities entity.
 */
public class OpportunitiesDTO implements Serializable {

    private Long id;

    private String opportunityCode;

    private OpportunitiesTypeEnums opportunitiesType;

    private String name;

    private String status;

    private String area;

    private String externalId;

    private Boolean highlighted;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Integer quantity;

    private String logoDesktopUrl;

    private String logoMobileUrl;

    private String hiringType;

    private String slug;

    private Long customersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpportunityCode() {
        return opportunityCode;
    }

    public void setOpportunityCode(String opportunityCode) {
        this.opportunityCode = opportunityCode;
    }

    public OpportunitiesTypeEnums getOpportunitiesType() {
        return opportunitiesType;
    }

    public void setOpportunitiesType(OpportunitiesTypeEnums opportunitiesType) {
        this.opportunitiesType = opportunitiesType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(Boolean highlighted) {
        this.highlighted = highlighted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getHiringType() {
        return hiringType;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getCustomersId() {
        return customersId;
    }

    public void setCustomersId(Long customersId) {
        this.customersId = customersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OpportunitiesDTO opportunitiesDTO = (OpportunitiesDTO) o;
        if (opportunitiesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), opportunitiesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OpportunitiesDTO{" +
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
            ", customers=" + getCustomersId() +
            "}";
    }
}
