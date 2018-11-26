package cz.fi.muni.pa165.secretagency.dto;

import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for creating new report. There is no need to override equals and hashcode
 *   because this class should not be used in a collection.
 *
 * @author Jan Pavlu (487548)
 */
public class ReportCreateDTO {

    @NotNull
    @Size(min = 10)
    private String text;

    @NotNull
    private MissionResultReportEnum missionResult;

    @NotNull
    private Long agentId;

    @NotNull
    private Long missionId;

    // GETTERS AND SETTERS
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public String toString() {
        return "ReportCreateDTO{" +
                "text='" + text + '\'' +
                ", missionResult=" + missionResult +
                ", agentId=" + agentId +
                ", missionId=" + missionId +
                '}';
    }
}
