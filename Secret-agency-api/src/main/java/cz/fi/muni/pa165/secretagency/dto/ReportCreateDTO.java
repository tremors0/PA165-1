package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for creating new report.
 *
 * @author Jan Pavlu
 */
public class ReportCreateDTO {

    private String text;
    private LocalDate date;
    private ReportStatus reportStatus = ReportStatus.NEW;
    private MissionResultReportEnum missionResult;
    private Long agentId;
    private Long missionId;

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

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    // automatically generated equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportCreateDTO)) return false;
        ReportCreateDTO that = (ReportCreateDTO) o;
        return Objects.equals(getText(), that.getText()) &&
                Objects.equals(getDate(), that.getDate()) &&
                getReportStatus() == that.getReportStatus() &&
                getMissionResult() == that.getMissionResult() &&
                Objects.equals(getAgentId(), that.getAgentId()) &&
                Objects.equals(getMissionId(), that.getMissionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getDate(), getReportStatus(), getMissionResult(), getAgentId(), getMissionId());
    }
}
