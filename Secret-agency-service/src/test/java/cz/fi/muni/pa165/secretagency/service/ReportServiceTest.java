package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.ReportDao;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * Tests for report service class.
 *
 * @author Jan Pavlu (487548)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReportServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ReportDao reportDao;

    @Autowired
    private ReportService reportService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(reportService, "dao", reportDao);
    }

    @Test
    public void getReportsFromIntervalOk () {
        Report report_january_1 = new Report();
        LocalDate report_from = LocalDate.of(1990, 1, 1);
        report_january_1.setId(1L);
        report_january_1.setDate(report_from);

        Report report_january_2 = new Report();
        LocalDate report_to = LocalDate.of(1990, 12, 31);
        report_january_2.setId(2L);
        report_january_2.setDate(report_to);

        List<Report> reportsFromJanuary = Arrays.asList(report_january_1, report_january_2);

        when(reportDao.getReportsFromInterval(report_from, report_to)).thenReturn(reportsFromJanuary);
        Assert.assertEquals(reportService.getReportsFromInterval(report_from, report_to).size(),
                reportsFromJanuary.size());
    }
}
