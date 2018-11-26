package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Set<AgentDTO> agents = new HashSet<>();
    private Set<ReportDTO> reports = new HashSet<>();

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

    public Set<AgentDTO> getAgents() {
        return agents;
    }

    public void setAgents(Set<AgentDTO> agents) {
        this.agents = agents;
    }

    public Set<ReportDTO> getReports() {
        return reports;
    }

    public void setReports(Set<ReportDTO> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionDTO)) return false;
        MissionDTO that = (MissionDTO) o;
        return Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                getMissionType() == that.getMissionType() &&
                Objects.equals(getStarted(), that.getStarted()) &&
                Objects.equals(getEnded(), that.getEnded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude(), getMissionType(), getStarted(), getEnded());
    }

    @Override
    public String toString() {
        return "MissionDTO{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", missionType=" + missionType +
                ", started=" + started +
                ", ended=" + ended +
                ", agents=" + agents +
                ", reports=" + reports +
                '}';
    }
}
