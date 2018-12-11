package cz.fi.muni.pa165.secretagency.controllers;

import cz.fi.muni.pa165.secretagency.dto.ReportCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportUpdateTextDTO;
import cz.fi.muni.pa165.secretagency.facade.ReportFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportFacade reportFacade;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReportDTO> getAll() {
        return reportFacade.getAllReports();
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public ReportDTO getReportById(@PathVariable Long id) {
        ReportDTO reportDTO = reportFacade.getReportById(id);
        if (reportDTO == null) {
            // TODO - throw exception
        }
        return reportDTO;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ReportDTO createReport(@Valid @RequestBody ReportCreateDTO reportCreateDTO) {
        // TODO - wrap with custom exception
        Long createdReportId = reportFacade.createReport(reportCreateDTO);
        return reportFacade.getReportById(createdReportId);
    }

    @RequestMapping(value = "/report/{id}", method = RequestMethod.DELETE)
    public void deleteReport(@PathVariable Long id) {
        // TODO - wrap with custom exception
        reportFacade.deleteReport(id);
    }

    @RequestMapping(value = "report/{id}", method = RequestMethod.PUT)
    public ReportDTO updateReport(@PathVariable Long id, @Valid @RequestBody ReportUpdateTextDTO reportUpdateTextDTO) {
        // TODO - wrap with custom exception
        reportFacade.updateReportText(reportUpdateTextDTO);
        return reportFacade.getReportById(id);
    }

}
