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
    private String name;
    private Double latitude;
    private Double longitude;
    private MissionTypeEnum missionType;
    private LocalDate started;
    private LocalDate ended;
    private Set<Long> agentIds = new HashSet<>();
    private Set<Long> reportIds = new HashSet<>();

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

    public Set<Long> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(Set<Long> agentIds) {
        this.agentIds = agentIds;
    }

    public Set<Long> getReportIds() {
        return reportIds;
    }

    public void setReportIds(Set<Long> reportIds) {
        this.reportIds = reportIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MissionDTO)) return false;
        MissionDTO that = (MissionDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "MissionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", missionType=" + missionType +
                ", started=" + started +
                ", ended=" + ended +
                ", agentIds=" + agentIds +
                ", reportIds=" + reportIds +
                '}';
    }
}
