package cz.fi.muni.pa165.secretagency.entity;

import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Mission entity for Secret agency project.
 *
 * @author Adam Skurla (487588)
 */
@Entity
public class Mission implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

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
     * @return name of the mission
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of the mission
     */
    public void setName(String name) {
        this.name = name;
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
        return Collections.unmodifiableSet(agents);
    }

    /**
     * @return reports from mission
     */
    public Set<Report> getReports() {
        return Collections.unmodifiableSet(reports);
    }

    /**
     * @param ended end date of mission
     */
    public void setEnded(LocalDate ended) {
        this.ended = ended;
    }

    /**
     * Add report about mission from agent.
     * @param report report about mission
     * @throws NullPointerException when report or agent is null
     */
    public void addReport(Report report, Agent agent) {
        if (report == null) {
            throw new NullPointerException("Cannot add report for mission when report is null");
        }
        if (agent == null) {
            throw new NullPointerException("Cannot add report for mission when agent is null");
        }
        this.reports.add(report);
        agent.addReport(report);
        report.setMission(this);
        report.setAgent(agent);
    }

    /**
     * Remove report about mission from agent.
     * @param report report about mission
     * @throws NullPointerException when report is or agent is null
     */
    public void removeReport(Report report, Agent agent) {
        if (report == null) {
            throw new NullPointerException("Cannot remove report for mission when report is null");
        }
        if (agent == null) {
            throw new NullPointerException("Cannot remove report for mission when agent is null");
        }
        this.reports.remove(report);
        agent.removeReport(report);
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

    /**
     * @param agent agent, which should be removed
     * @throws NullPointerException when agent is null
     */
    public void removeAgent(Agent agent) {
        if (agent == null) {
            throw new NullPointerException("Cannot remove agent from mission if agent is null");
        }
        this.agents.remove(agent);
        agent.removeMission(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mission)) return false;
        Mission mission = (Mission) o;
        return Objects.equals(getName(), mission.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
