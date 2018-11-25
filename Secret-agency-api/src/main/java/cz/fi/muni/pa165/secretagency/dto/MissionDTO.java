package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Mission DTO
 * @author Adam Skurla (487588)
 */
public class MissionDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    private MissionTypeEnum missionType;
    private LocalDate started;
    private LocalDate ended;
    // private Set<AgentDTO> agents;
    // private Set<ReportDTO> reports;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionDTO)) return false;
        MissionDTO missionDTO = (MissionDTO) o;
        return Objects.equals(getLatitude(), missionDTO.getLatitude()) &&
                Objects.equals(getLongitude(), missionDTO.getLongitude()) &&
                getMissionType() == missionDTO.getMissionType() &&
                Objects.equals(getStarted(), missionDTO.getStarted()) &&
                Objects.equals(getEnded(), missionDTO.getEnded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude(), getMissionType(), getStarted(), getEnded());
    }
}
