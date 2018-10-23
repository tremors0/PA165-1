package cz.fi.muni.pa165.secretagency.entity;

import java.util.Objects;

/**
 * Department entity for Secret Agency.
 *
 * @author Milos Silhar (433614)
 */
public class Department {

    private Long id;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;

    public Department() {
    }

    /**
     * @return Id of department
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id of department
     * @param id Department's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return City of department
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city of department
     * @param city Department's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return Country of department
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country for department
     * @param country Department's country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return Latitude coordinate of department's location
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude coordinate of department
     * @param latitude Department's latitude coordinate
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return Longitude coordinate of department's location
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude coordinate of department
     * @param longitude Department's longitude coordinate
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCountry(), getLatitude(), getLongitude());
    }
}
