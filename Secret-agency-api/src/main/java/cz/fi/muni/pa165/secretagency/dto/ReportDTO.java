package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for report.
 *
 * @author Jan Pavlu (487548)
 */
public class ReportDTO {

    private Long id;
    private String text;
    private LocalDate date;
    private ReportStatus reportStatus = ReportStatus.NEW;
    private MissionResultReportEnum missionResult;
    private MissionDTO mission;
    private AgentDTO agent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public MissionResultReportEnum getMissionResult() {
        return missionResult;
    }

    public void setMissionResult(MissionResultReportEnum missionResult) {
        this.missionResult = missionResult;
    }

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    public MissionDTO getMission() {
        return mission;
    }

    public void setMission(MissionDTO mission) {
        this.mission = mission;
    }

    // automatically generated equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportDTO)) return false;
        ReportDTO reportDTO = (ReportDTO) o;
        return Objects.equals(getText(), reportDTO.getText()) &&
                Objects.equals(getDate(), reportDTO.getDate()) &&
                getReportStatus() == reportDTO.getReportStatus() &&
                getMissionResult() == reportDTO.getMissionResult();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getDate(), getReportStatus(), getMissionResult());
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", reportStatus=" + reportStatus +
                ", missionResult=" + missionResult +
                ", mission=" + mission +
                ", agent=" + agent +
                '}';
    }
}
