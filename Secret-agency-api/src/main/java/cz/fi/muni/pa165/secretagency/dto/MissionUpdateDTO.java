package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for updating Mission.
 * @author Milos Silhar (433614)
 */
public class MissionUpdateDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private MissionTypeEnum missionType;

    @NotNull
    private LocalDate started;

    private LocalDate ended;

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

    public MissionTypeEnum getMissionType() {
        return missionType;
    }

    public void setMissionType(MissionTypeEnum missionType) {
        this.missionType = missionType;
    }

    public LocalDate getStarted() {
        return started;
    }

    public void setStarted(LocalDate started) {
        this.started = started;
    }

    public LocalDate getEnded() {
        return ended;
    }

    public void setEnded(LocalDate ended) {
        this.ended = ended;
    }

    @Override
    public String toString() {
        return "MissionUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", missionType=" + missionType +
                ", started=" + started +
                ", ended=" + ended +
                '}';
    }
}
