package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collections;

@ContextConfiguration(classes = SecretAgencyPersistenceApplicationContext.class)
public class ReportDaoTests extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManagerFactory emf;

    @Autowired
    private ReportDao reportDao;

    private Report reportNew;
    private Report reportApproved;
    private Report reportDenied;

    private Mission mission;
    private Agent agent;
    private Department department;

    @BeforeClass
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Mission mission = new Mission();
        mission.setStarted(LocalDate.of(2013, 12, 1));
        mission.setLongitude(4d);
        mission.setLatitude(4d);
        mission.setMissionType(MissionTypeEnum.ESPIONAGE);
        em.persist(mission);
        this.mission = mission;

        Agent agent = new Agent();
        agent.setBirthDate(LocalDate.of(1976, 4, 9));
        agent.setRank(AgentRankEnum.SENIOR);
        agent.setName("James Bond");
        agent.setCodeName("007");
        agent.setLanguages(Collections.singleton(LanguageEnum.EN));
        em.persist(agent);
        this.agent = agent;

        Department department = new Department();
        department.setLongitude(10d);
        department.setLatitude(10d);
        department.setCountry("moon");
        department.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        em.persist(department);
        department.addAgent(agent);
        this.department = department;

        Report reportNew = new Report();
        reportNew.setText("new report");
        reportNew.setMission(mission);
        reportNew.setAgent(agent);
        reportNew.setDate(LocalDate.of(2015,10,11));
        reportNew.setMissionResult(MissionResultReportEnum.COMPLETED);
        reportNew.setReportStatus(ReportStatus.NEW);
        em.persist(reportNew);
        this.reportNew = reportNew;



        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void saveReport() {

    }

    @Test
    public void deleteReport() {

    }

    @Test
    public void deleteReportById() {

    }

    @Test
    public void getReportById() {

    }

    @Test
    public void getAllReports() {

    }

    @Test
    public void getReportsFromInterval() {

    }

    @Test
    public void getReportsFromIntervalFromBorderValue() {

    }

    @Test
    public void getReportsFromIntervalToBorderValue() {

    }

    @Test
    public void getReportsWithResult() {

    }

    @Test
    public void getReportsWithResultNoFound() {

    }

    @Test
    public void getReportsWithStatus() {

    }

    @Test
    public void getReportsWithStatusNoFound() {

    }

    @Test
    public void getReportsWithStatusFromMission() {

    }

    @Test
    public void getReportsWithStatusFromMissionNoFound() {

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getReportsWithStatusFromMissionNull() {

    }
}
