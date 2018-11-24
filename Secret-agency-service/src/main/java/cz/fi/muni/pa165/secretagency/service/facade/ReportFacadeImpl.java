package cz.fi.muni.pa165.secretagency.service.facade;

import cz.fi.muni.pa165.secretagency.dto.ReportCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportUpdateTextDTO;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;
import cz.fi.muni.pa165.secretagency.facade.ReportFacade;
//import cz.fi.muni.pa165.secretagency.service.AgentService;
import cz.fi.muni.pa165.secretagency.service.BeanMappingService;
import cz.fi.muni.pa165.secretagency.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of facade for report.
 *
 * @author Jan Pavlu
 */
@Service
@Transactional
public class ReportFacadeImpl implements ReportFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private ReportService reportService;

    //@Autowired
    //private AgentService agentService;

    // TODO pavlu - uncomment after MissionService is created
    // @Autowired
    // private MissionService missionService;

    @Override
    public List<ReportDTO> getAllReports() {
        return beanMappingService.mapTo(reportService.getAll(), ReportDTO.class);
    }

    @Override
    public ReportDTO getReportById(Long id) {
        Report report = reportService.getEntityById(id);
        return report == null ? null : beanMappingService.mapTo(report, ReportDTO.class);
    }

    @Override
    public void createReport(ReportCreateDTO reportDTO) {
        // TODO pavlu - uncomment after mission service exists
//        Agent agent = agentService.getEntityById(reportDTO.getAgentId());
//        Mission mission = missionService.getEntiryById(reportDTO.getMissionId());
//
//        Report report = new Report();
//        report.setMission(mission);
//        report.setMissionResult(reportDTO.getMissionResult());
//        report.setReportStatus(reportDTO.getReportStatus());
//        report.setDate(reportDTO.getDate());
//        report.setText(reportDTO.getText());
//
//        reportService.save(report);
    }

    @Override
    public void deleteReport(Long reportId) {
        reportService.deleteEntityById(reportId);
    }

    @Override
    public void updateReportText(ReportUpdateTextDTO reportDTO) {
        Report report = reportService.getEntityById(reportDTO.getId());
        reportService.updateReportText(report, reportDTO.getText());
    }

    @Override
    public void approveReport(Long reportId) {
        Report report = reportService.getEntityById(reportId);
        reportService.changeReportStatus(report, ReportStatus.APPROVED);
    }

    @Override
    public void denyReport(Long reportId) {
        Report report = reportService.getEntityById(reportId);
        reportService.changeReportStatus(report, ReportStatus.DENIED);
    }

    @Override
    public List<ReportDTO> getReportsFromInterval(LocalDate dateFrom, LocalDate dateTo) {
        return beanMappingService.mapTo(reportService.getReportsFromInterval(dateFrom, dateTo), ReportDTO.class);
    }

    @Override
    public List<ReportDTO> getReportsWithResult(MissionResultReportEnum missionResultReport) {
        return beanMappingService.mapTo(reportService.getReportsWithResult(missionResultReport), ReportDTO.class);
    }

    @Override
    public List<ReportDTO> getReportsWithStatus(ReportStatus reportStatus) {
        return beanMappingService.mapTo(reportService.getReportsWithStatus(reportStatus), ReportDTO.class);
    }

    @Override
    public List<ReportDTO> getReportsWithStatusFromMission(ReportStatus reportStatus, Long missionId) {
        // TODO pavlu - uncomment, after mission service is created
        // Mission mission = mission.getEntityById(missionId);
        // TODO pavlu - check if mission exists???
        //return beanMappingService.mapTo(reportService.getReportsWithStatusFromMission(reportStatus, mission),
        //        ReportDTO.class);
        return null;
    }
}
