package main.cz.fi.muni.pa165.secretagency.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Mission entity for Secret agency project.
 *
 * @author Adam Skurla (487588)
 */
public class Mission {
    private Long id;
    private Double[] location;
    private String missionType;
    private LocalDate started;
    private LocalDate ended;

    /**
     * Constructor
     * @param id of mission
     * @param location of mission (latitude and longtitude)
     * @param missionType type of mission
     * @param started mission start date
     * @param ended mission end date
     */
    public Mission(Long id, Double[] location, String missionType, LocalDate started, LocalDate ended) {
        this.id = id;
        this.location = location;
        this.missionType = missionType;
        this.started = started;
        this.ended = ended;
    }

    /**
     * Create empty Mission
     */
    public Mission() {
    }

    /**
     * @return mission id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id mission id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return mission location
     */
    public Double[] getLocation() {
        return location;
    }

    /**
     * @param location of mission
     */
    public void setLocation(Double[] location) {
        this.location = location;
    }

    /**
     * @return mission type
     */
    public String getMissionType() {
        return missionType;
    }

    /**
     * @param missionType type of mission
     */
    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    /**
     * @return mission start date
     */
    public LocalDate getStarted() {
        return started;
    }

    /**
     * @param started start date of mission
     */
    public void setStarted(LocalDate started) {
        this.started = started;
    }

    /**
     * @return mission end date
     */
    public LocalDate getEnded() {
        return ended;
    }

    /**
     * @param ended end date of mission
     */
    public void setEnded(LocalDate ended) {
        this.ended = ended;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Mission)) return false;

        Mission mission = (Mission) object;
        return Objects.equals(getId(), mission.getId()) &&
                Arrays.equals(getLocation(), mission.getLocation()) &&
                Objects.equals(getMissionType(), mission.getMissionType()) &&
                Objects.equals(getStarted(), mission.getStarted()) &&
                Objects.equals(getEnded(), mission.getEnded());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), id, missionType, started, ended);
        result = 31 * result + Arrays.hashCode(location);
        return result;
    }
}
