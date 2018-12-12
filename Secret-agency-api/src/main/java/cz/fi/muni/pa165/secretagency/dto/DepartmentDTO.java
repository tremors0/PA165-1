package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import java.util.*;

/**
 * DTO for Department
 *
 * @author Milos Silhar (433614)
 */
public class DepartmentDTO {

    private Long id;

    private String city;

    private String country;

    private Double latitude;

    private Double longitude;

    private DepartmentSpecialization specialization;

    private Set<Long> agentIds = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public DepartmentSpecialization getSpecialization() { return specialization; }
    public void setSpecialization(DepartmentSpecialization specialization) { this.specialization = specialization; }

    public Set<Long> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(Set<Long> agentIds) {
        this.agentIds = agentIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentDTO)) return false;
        DepartmentDTO that = (DepartmentDTO) o;
        return Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                getSpecialization() == that.getSpecialization();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCountry(), getLatitude(), getLongitude(), getSpecialization());
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", specialization=" + specialization +
                ", agentIds=" + agentIds +
                '}';
    }
}
