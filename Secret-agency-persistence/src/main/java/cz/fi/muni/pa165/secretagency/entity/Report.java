package cz.fi.muni.pa165.secretagency.entity;

import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Report entity for Secret agency project.
 *
 * @author Jan Pavlu (487548)
 */
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String text;

    @Temporal(TemporalType.DATE)
    @NotNull
    private LocalDate date;

    @Enumerated
    @NotNull
    private MissionResultReportEnum missionResult;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    @NotNull
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    @NotNull
    private Agent agent;

    /**
     * Constructor
     * @param text text of the report
     * @param date date when report was created
     * @param missionResult result of agent's work during the mission
     * @param mission Mission, which is described by this report
     * @param agent Author of report
     */
    public Report(String text, LocalDate date, MissionResultReportEnum missionResult,
                  Mission mission, Agent agent) {
        this.text = text;
        this.date = date;
        this.missionResult = missionResult;
        this.mission = mission;
        this.agent = agent;
    }

    /**
     * Empty constructor
     */
    public Report() {
    }

    // GETTERS AND SETTERS
    /**
     * @return id of the report
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id of the report
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return text of the report
     */
    public String getText() {
        return text;
    }

    /**
     * @param text text of the report
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return date when report was created
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date date when report was created
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return result of agent's work during the mission
     */
    public MissionResultReportEnum getMissionResult() {
        return missionResult;
    }

    /**
     * @param missionResult result of agent's work during the mission
     */
    public void setMissionResult(MissionResultReportEnum missionResult) {
        this.missionResult = missionResult;
    }

    /**
     * @return mission, which is described by this report
     */
    public Mission getMission() {
        return mission;
    }

    /**
     * @param mission Mission described by report
     */
    public void setMission(Mission mission) {
        this.mission = mission;
    }

    /**
     * @return Agent who wrote the report
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * @param agent Author of the report
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;

        Report report = (Report) o;

        if (!getText().equals(report.getText())) return false;
        if (!getDate().equals(report.getDate())) return false;
        return getMissionResult() == report.getMissionResult();
    }

    @Override
    public int hashCode() {
        int result = getText().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getMissionResult().hashCode();
        return result;
    }
}
