package br.com.connekt.plataforma.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Candidates entity.
 */
public class CandidatesDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String celPhone;

    private String area;

    private Instant dataOfBirth;

    private String occupation;

    private String pictureUrl;

    private String salesForceId;

    private Long placesId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Instant getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Instant dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSalesForceId() {
        return salesForceId;
    }

    public void setSalesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
    }

    public Long getPlacesId() {
        return placesId;
    }

    public void setPlacesId(Long placesId) {
        this.placesId = placesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatesDTO candidatesDTO = (CandidatesDTO) o;
        if (candidatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidatesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", celPhone='" + getCelPhone() + "'" +
            ", area='" + getArea() + "'" +
            ", dataOfBirth='" + getDataOfBirth() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", pictureUrl='" + getPictureUrl() + "'" +
            ", salesForceId='" + getSalesForceId() + "'" +
            ", places=" + getPlacesId() +
            "}";
    }
}
