package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import javax.validation.constraints.NotNull;

/**
 * DTO for editing specialization of existing department
 *
 * @author Milos Silhar (433614)
 */
public class DepartmentUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private DepartmentSpecialization specialization;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public DepartmentSpecialization getSpecialization() { return specialization; }
    public void setSpecialization(DepartmentSpecialization specialization) { this.specialization = specialization; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DepartmentUpdateDTO{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
