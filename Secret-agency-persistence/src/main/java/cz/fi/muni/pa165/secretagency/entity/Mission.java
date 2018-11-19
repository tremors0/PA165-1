package cz.fi.muni.pa165.secretagency.entity;

import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Mission entity for Secret agency project.
 *
 * @author Adam Skurla (487588)
 */
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Enumerated
    private MissionTypeEnum missionType;

    @NotNull
    private LocalDate started;

    @Nullable
    private LocalDate ended;

    @ManyToMany(mappedBy = "missions")
    private Set<Agent> agents = new HashSet<>();

    @OneToMany(mappedBy = "mission")
    private Set<Report> reports = new HashSet<>();

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
     * @return latitude of mission location
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude of mission location
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return longitude of mission location
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude of mission location
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return mission type
     */
    public MissionTypeEnum getMissionType() {
        return missionType;
    }

    /**
     * @param missionType type of mission
     */
    public void setMissionType(MissionTypeEnum missionType) {
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
     * @return agents on mission
     */
    public Set<Agent> getAgents() {
        return agents;
    }

    /**
     * @param agents agents on mission
     */
    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }

    /**
     * @return reports from mission
     */
    public Set<Report> getReports() {
        return reports;
    }

    /**
     * @param reports reports from mission
     */
    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    /**
     * @param ended end date of mission
     */
    public void setEnded(LocalDate ended) {
        this.ended = ended;
    }

    /**
     * Add report about mission
     * @param report report about mission
     * @throws NullPointerException when report is null
     */
    public void addReport(Report report) {
        if (report == null) {
            throw new NullPointerException("Cannot add report for mission when report is null");
        }
        this.reports.add(report);
        report.setMission(this);
    }

    /**
     * @param agent agent, which should be added
     * @throws NullPointerException when agent is null
     */
    public void addAgent(Agent agent) {
        if (agent == null) {
            throw new NullPointerException("Cannot add agent on mission if agent is null");
        }
        this.agents.add(agent);
        agent.addMission(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Mission)) return false;

        Mission mission = (Mission) object;
        return Objects.equals(getLatitude(), mission.getLatitude()) &&
                Objects.equals(getLongitude(), mission.getLongitude()) &&
                Objects.equals(getMissionType(), mission.getMissionType()) &&
                Objects.equals(getStarted(), mission.getStarted()) &&
                Objects.equals(getEnded(), mission.getEnded());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude(), getMissionType(), getStarted(), getEnded());
    }
}
