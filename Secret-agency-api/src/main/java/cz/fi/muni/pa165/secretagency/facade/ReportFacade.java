package cz.fi.muni.pa165.secretagency.facade;

import cz.fi.muni.pa165.secretagency.dto.ReportCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportUpdateTextDTO;
import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Facade for report.
 *
 * @author Jan Pavlu
 */
public interface ReportFacade {

    /**
     * Return all existing reports.
     * @return all existing reports.
     */
    List<ReportDTO> getAllReports();

    /**
     * Return report with given id.
     * @param id report id
     * @return report with given id
     */
    ReportDTO getReportById(Long id);

    /**
     * Creates report.
     * @param report data about report
     */
    void createReport(ReportCreateDTO report);

    /**
     * Deletes report with given id.
     * @param reportId report id
     */
    void deleteReport(Long reportId);

    /**
     * Updates text in selected report.
     * @param report updated data
     */
    void updateReport(ReportUpdateTextDTO report);

    /**
     * Method which should be used for approving report.
     */
    void approveReport();

    /**
     * Method which should be used for denying report.
     */
    void denyReport();

    /**
     * Get all reports in given interval
     * @param dateFrom bottom border of interval
     * @param dateTo top border of interval
     * @return all reports within interval
     */
    List<ReportDTO> getReportsFromInterval(LocalDate dateFrom, LocalDate dateTo);

    /**
     * Get all reports with specific mission result report
     * @param missionResultReport mission result report
     * @return all reports with specified mission result report
     */
    List<ReportDTO> getReportsWithResult(MissionResultReportEnum missionResultReport);

    /**
     * Get all reports with specified report status
     * @param reportStatus report status
     * @return all reports with specified report status
     */
    List<ReportDTO> getReportsWithStatus(ReportStatus reportStatus);

    /**
     * Get all reports with specified status from the mission
     * @param reportStatus report status
     * @param missionId mission id
     * @return all reports with specified status from the mission
     */
    List<ReportDTO> getReportsWithStatusFromMission(ReportStatus reportStatus, Long missionId);

}
