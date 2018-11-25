package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DTO for department used when creating new
 *
 * @author Milos Silhar (433614)
 */
public class DepartmentCreateDTO {

    private String city;

    @NotNull
    private String country;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    private DepartmentSpecialization specialization;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public DepartmentSpecialization getSpecialization() { return specialization; }
    public void setSpecialization(DepartmentSpecialization specialization) { this.specialization = specialization; }

    @Override
    public String toString() {
        return "DepartmentCreateDTO{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", specialization=" + specialization +
                '}';
    }
}
