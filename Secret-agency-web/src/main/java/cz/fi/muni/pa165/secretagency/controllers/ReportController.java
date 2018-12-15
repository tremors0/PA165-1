package cz.fi.muni.pa165.secretagency.controllers;

import cz.fi.muni.pa165.secretagency.ApiUris;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportUpdateTextDTO;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;
import cz.fi.muni.pa165.secretagency.exceptions.AuthorizationException;
import cz.fi.muni.pa165.secretagency.exceptions.ResourceNotFoundException;
import cz.fi.muni.pa165.secretagency.facade.ReportFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static cz.fi.muni.pa165.secretagency.security.AuthenticationSuccessHandler.AUTHENTICATED_USER_SESSION_KEY;

/**
 * Controller for report.
 *
 * @author Jan Pavlu (487548)
 */
@RestController
@RequestMapping({ApiUris.ROOT_URI_REPORTS, ApiUris.ROOT_URI_REST + ApiUris.ROOT_URI_REPORTS})
public class ReportController {

    final static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportFacade reportFacade;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReportDTO> getAll() {
        logger.debug("Get All reports");
        return reportFacade.getAllReports();
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public ReportDTO getReportById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.debug("Get report with id {}", id);
        ReportDTO reportDTO = reportFacade.getReportById(id);
        if (reportDTO == null) {
            throw new ResourceNotFoundException();
        }
        return reportDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ReportDTO createReport(@Valid @RequestBody ReportCreateDTO reportCreateDTO) {
        logger.debug("Create report with values: {}", reportCreateDTO);
        Long createdReportId = reportFacade.createReport(reportCreateDTO);
        return reportFacade.getReportById(createdReportId);
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.DELETE)
    public void deleteReport(@PathVariable Long id) {
        logger.debug("Delete report with id {}", id);
        try {
            reportFacade.deleteReport(id);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.PUT)
    public ReportDTO updateReport(@PathVariable Long id, @Valid @RequestBody ReportUpdateTextDTO reportUpdateTextDTO) {
        logger.debug("Update report text for report {}. New value is {}", id, reportUpdateTextDTO);
        try {
            reportFacade.updateReportText(reportUpdateTextDTO);
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException();
        }
        return reportFacade.getReportById(id);
    }

    @RequestMapping(value = "/report/{id}/approve", method = RequestMethod.PUT)
    public void approveReport(@PathVariable Long id, HttpServletRequest request) {
        logger.debug("Approve report with id {}", id);
        AgentDTO authenticatedAgent = (AgentDTO) request.getSession().getAttribute(AUTHENTICATED_USER_SESSION_KEY);
        if (authenticatedAgent == null) {
            throw new RuntimeException("Unable to retrieve data about authenticated user");
        }
        if (authenticatedAgent.getRank() != AgentRankEnum.AGENT_IN_CHARGE) {
            throw new AuthorizationException();
        }
        reportFacade.approveReport(id);
    }

    @RequestMapping(value = "/report/{id}/deny", method = RequestMethod.PUT)
    public void denyReport(@PathVariable Long id, HttpServletRequest request) {
        logger.debug("Deny report with id {}", id);
        AgentDTO authenticatedAgent = (AgentDTO) request.getSession().getAttribute(AUTHENTICATED_USER_SESSION_KEY);
        if (authenticatedAgent == null) {
            throw new RuntimeException("Unable to retrieve data about authenticated user");
        }
        if (authenticatedAgent.getRank() != AgentRankEnum.AGENT_IN_CHARGE) {
            throw new AuthorizationException();
        }
        reportFacade.approveReport(id);
    }

    @RequestMapping(value = "/interval/{dateFrom}/{dateTo}")
    public List<ReportDTO> getReportsFromInterval(@PathVariable LocalDate dateFrom, @PathVariable LocalDate dateTo) {
        logger.debug("Get reports from interval {} - {}", dateFrom.toString(), dateTo.toString());
        return reportFacade.getReportsFromInterval(dateFrom, dateTo);
    }

    @RequestMapping(value = "/missonResult/{missionResult}")
    public List<ReportDTO> getReportsWithResult(@PathVariable MissionResultReportEnum missionResult) {
        logger.debug("Get reports with result {}", missionResult);
        return reportFacade.getReportsWithResult(missionResult);
    }

    @RequestMapping(value = "/reportStatus/{reportStatus}")
    public List<ReportDTO> getReportsWithStatus(@PathVariable ReportStatus reportStatus) {
        logger.debug("Get reports with status {}", reportStatus);
        return reportFacade.getReportsWithStatus(reportStatus);
    }

    @RequestMapping(value = "/report/{reportId}/mentionedAgents")
    public Set<AgentDTO> getAgentsMentionedInReport(@PathVariable  Long reportId) {
        logger.debug("Get agents mentioned in report {}", reportId);
        Set<AgentDTO> mentionedAgents;
        try {
            mentionedAgents = reportFacade.getAgentsMentionedInReport(reportId);
        } catch (NullPointerException ex) {
            throw new ResourceNotFoundException();
        }
        return mentionedAgents;
    }

}
