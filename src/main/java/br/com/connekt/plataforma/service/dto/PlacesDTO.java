package br.com.connekt.plataforma.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Places entity.
 */
public class PlacesDTO implements Serializable {

    private Long id;

    private String address;

    private String district;

    private String city;

    private String zone;

    private String stateProvince;

    private String country;

    private String zipCode;

    private Long opportunitiesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getOpportunitiesId() {
        return opportunitiesId;
    }

    public void setOpportunitiesId(Long opportunitiesId) {
        this.opportunitiesId = opportunitiesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlacesDTO placesDTO = (PlacesDTO) o;
        if (placesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), placesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlacesDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", district='" + getDistrict() + "'" +
            ", city='" + getCity() + "'" +
            ", zone='" + getZone() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", opportunities=" + getOpportunitiesId() +
            "}";
    }
}
