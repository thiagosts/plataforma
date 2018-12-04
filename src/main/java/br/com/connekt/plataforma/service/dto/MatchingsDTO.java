package br.com.connekt.plataforma.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Matchings entity.
 */
public class MatchingsDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private BigDecimal time;

    private String isDefault;

    private Long customizationId;

    private Long matchingsjobId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Long getCustomizationId() {
        return customizationId;
    }

    public void setCustomizationId(Long customizationId) {
        this.customizationId = customizationId;
    }

    public Long getMatchingsjobId() {
        return matchingsjobId;
    }

    public void setMatchingsjobId(Long matchingsJobId) {
        this.matchingsjobId = matchingsJobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchingsDTO matchingsDTO = (MatchingsDTO) o;
        if (matchingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matchingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatchingsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", time=" + getTime() +
            ", isDefault='" + getIsDefault() + "'" +
            ", customization=" + getCustomizationId() +
            ", matchingsjob=" + getMatchingsjobId() +
            "}";
    }
}
