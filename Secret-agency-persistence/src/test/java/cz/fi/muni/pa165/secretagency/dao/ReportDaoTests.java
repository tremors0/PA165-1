package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Tests for ReportDao interface. These test don't cover edge cases (exceptions)
 * because those will be handled by Service layer.
 *
 * @author Milos Silhar (433614)
 */
@ContextConfiguration(classes = SecretAgencyPersistenceApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ReportDaoTests extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private ReportDao reportDao;

    private Report reportNew;

    private Mission mission;

    @BeforeClass
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Mission mission = new Mission();
        mission.setStarted(LocalDate.of(2013, 12, 1));
        mission.setLongitude(4d);
        mission.setLatitude(4d);
        mission.setMissionType(MissionTypeEnum.ESPIONAGE);
        mission.setName("Spy in girl's shower");
        em.persist(mission);
        this.mission = mission;

        Department department = new Department();
        department.setLongitude(10d);
        department.setLatitude(10d);
        department.setCountry("moon");
        department.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        em.persist(department);

        Agent agent = new Agent();
        agent.setBirthDate(LocalDate.of(1976, 4, 9));
        agent.setRank(AgentRankEnum.SENIOR);
        agent.setName("James Bond");
        agent.setCodeName("007");
        agent.setLanguages(Collections.singleton(LanguageEnum.EN));
        agent.setDepartment(department);
        em.persist(agent);

        department.addAgent(agent);

        Report reportNew = new Report();
        reportNew.setText("new report");
        reportNew.setMission(mission);
        reportNew.setAgent(agent);
        reportNew.setDate(LocalDate.of(2015,10,11));
        reportNew.setMissionResult(MissionResultReportEnum.COMPLETED);
        reportNew.setReportStatus(ReportStatus.NEW);
        em.persist(reportNew);
        this.reportNew = reportNew;

        Report reportApproved = new Report();
        reportApproved.setText("approved report");
        reportApproved.setMission(mission);
        reportApproved.setAgent(agent);
        reportApproved.setDate(LocalDate.of(2013,9,11));
        reportApproved.setMissionResult(MissionResultReportEnum.COMPLETED);
        reportApproved.setReportStatus(ReportStatus.APPROVED);
        em.persist(reportApproved);

        Report reportApproved02 = new Report();
        reportApproved02.setText("approved report");
        reportApproved02.setMission(mission);
        reportApproved02.setAgent(agent);
        reportApproved02.setDate(LocalDate.of(1980,8,11));
        reportApproved02.setMissionResult(MissionResultReportEnum.COMPLETED);
        reportApproved02.setReportStatus(ReportStatus.APPROVED);
        em.persist(reportApproved02);

        em.getTransaction().commit();
        em.close();
    }

    /**
     * Test getting report by specific id.
     */
    @Test
    public void getReportById() {
        Report found = reportDao.getEntityById(reportNew.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, reportNew);
    }

    /**
     * Tests getting report by id with is not in database.
     */
    @Test
    public void getReportByIdNotFound() {
        Report found = reportDao.getEntityById(123456l);
        Assert.assertNull(found);
    }

    /**
     * Tests getting all available reports.
     */
    @Test
    public void getAllReports() {
        List<Report> result = reportDao.getAll();
        Assert.assertEquals(result.size(), 3);
    }

    /**
     * Tests getting reports from specific date interval.
     */
    @Test
    public void getReportsFromInterval() {
        List<Report> result = reportDao.getReportsFromInterval(LocalDate.of(2000,1,1), LocalDate.of(2017,12,31));
        Assert.assertEquals(result.size(), 2);
    }

    /**
     * Tests getting reports from specific date interval, if fromDate is equal to date of one of the reports.
     */
    @Test
    public void getReportsFromIntervalFromBorderValue() {
        List<Report> result = reportDao.getReportsFromInterval(LocalDate.of(2013,9,11), LocalDate.of(2013,12,31));
        Assert.assertEquals(result.size(), 1);
    }

    /**
     * Tests getting reports from specific date interval, if toDate is equal to date of one of the reports.
     */
    @Test
    public void getReportsFromIntervalToBorderValue() {
        List<Report> result = reportDao.getReportsFromInterval(LocalDate.of(2015,1,1), LocalDate.of(2015,10,11));
        Assert.assertEquals(result.size(), 1);
    }

    /**
     * Tests getting reports with specific report result.
     */
    @Test
    public void getReportsWithResult() {
        List<Report> result = reportDao.getReportsWithResult(MissionResultReportEnum.COMPLETED);
        Assert.assertEquals(result.size(), 3);
    }

    /**
     * Tests getting reports with report result which is not in database.
     */
    @Test
    public void getReportsWithResultNoFound() {
        List<Report> result = reportDao.getReportsWithResult(MissionResultReportEnum.FAILED);
        Assert.assertEquals(result.size(), 0);
    }

    /**
     * Tests getting reports with specific status of report.
     */
    @Test
    public void getReportsWithStatus() {
        List<Report> result = reportDao.getReportsWithStatus(ReportStatus.APPROVED);
        Assert.assertEquals(result.size(), 2);
    }

    /**
     * Tests getting reports with status which is not in database.
     */
    @Test
    public void getReportsWithStatusNoFound() {
        List<Report> result = reportDao.getReportsWithStatus(ReportStatus.DENIED);
        Assert.assertEquals(result.size(), 0);
    }

    /**
     * Tests getting reports with specific status of report which has associated specific mission.
     */
    @Test
    public void getReportsWithStatusFromMission() {
        List<Report> result = reportDao.getReportsWithStatusFromMission(ReportStatus.NEW, mission);
        Assert.assertEquals(result.size(), 1);
    }

    /**
     * Tests getting reports with specific status which given mission has none.
     */
    @Test
    public void getReportsWithStatusFromMissionNoFound() {
        List<Report> result = reportDao.getReportsWithStatusFromMission(ReportStatus.DENIED, mission);
        Assert.assertEquals(result.size(), 0);
    }
}
