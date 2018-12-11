package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dto.AgentCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentDTO;
import cz.fi.muni.pa165.secretagency.dto.MissionCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.MissionDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.ReportDTO;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;
import cz.fi.muni.pa165.secretagency.enums.MissionResultReportEnum;
import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import cz.fi.muni.pa165.secretagency.enums.ReportStatus;
import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Collections;

/**
 * Tests for BeanMappingService class.
 *
 * @author Milos Silhar
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests
{

    @Autowired
    private BeanMappingService beanMappingService;

    @Test
    public void mapDepartmentCreateDtoToDepartmentTest() {
        DepartmentCreateDTO departmentCreateDTO = new DepartmentCreateDTO();
        departmentCreateDTO.setCity("london");
        departmentCreateDTO.setCountry("uk");
        departmentCreateDTO.setLatitude(2.0);
        departmentCreateDTO.setLongitude(4.0);
        departmentCreateDTO.setSpecialization(DepartmentSpecialization.ASSASSINATION);

        Department department = beanMappingService.mapTo(departmentCreateDTO, Department.class);

        Assert.assertNull(department.getId());
        Assert.assertEquals(department.getAgents().size(), 0);
        Assert.assertEquals(department.getCity(), departmentCreateDTO.getCity());
        Assert.assertEquals(department.getCountry(), departmentCreateDTO.getCountry());
        Assert.assertEquals(department.getLatitude(), departmentCreateDTO.getLatitude());
        Assert.assertEquals(department.getLongitude(), departmentCreateDTO.getLongitude());
        Assert.assertEquals(department.getSpecialization(), departmentCreateDTO.getSpecialization());
    }

    @Test
    public void mapAgentCreateDtoToAgentTest() {
        AgentCreateDTO agentCreateDTO = new AgentCreateDTO();
        agentCreateDTO.setName("bond");
        agentCreateDTO.setCodeName("007");
        agentCreateDTO.setBirthDate(LocalDate.of(2010, 12,11));
        agentCreateDTO.setLanguages(Collections.singleton(LanguageEnum.SK));
        agentCreateDTO.setRank(AgentRankEnum.TRAINEE);

        Agent agent = beanMappingService.mapTo(agentCreateDTO, Agent.class);

        Assert.assertNull(agent.getId());
        Assert.assertNull(agent.getDepartment());
        Assert.assertEquals(agent.getLanguages(), agentCreateDTO.getLanguages());
        Assert.assertEquals(agent.getMissions().size(), 0);
        Assert.assertEquals(agent.getReports().size(), 0);
        Assert.assertEquals(agent.getBirthDate(), agentCreateDTO.getBirthDate());
        Assert.assertEquals(agent.getCodeName(), agentCreateDTO.getCodeName());
        Assert.assertEquals(agent.getName(), agentCreateDTO.getName());
        Assert.assertEquals(agent.getRank(), agentCreateDTO.getRank());
    }

    @Test
    public void mapMissionCreateDtoToMissionTest() {
        MissionCreateDTO missionCreateDTO = new MissionCreateDTO();
        missionCreateDTO.setStarted(LocalDate.of(2010, 10, 11));
        missionCreateDTO.setEnded(LocalDate.of(2010, 11, 11));
        missionCreateDTO.setLatitude(12.0);
        missionCreateDTO.setLongitude(24.0);
        missionCreateDTO.setMissionType(MissionTypeEnum.ESPIONAGE);

        Mission mission = beanMappingService.mapTo(missionCreateDTO, Mission.class);

        Assert.assertNull(mission.getId());
        Assert.assertEquals(mission.getAgents().size(), 0);
        Assert.assertEquals(mission.getReports().size(), 0);
        Assert.assertEquals(mission.getStarted(), missionCreateDTO.getStarted());
        Assert.assertEquals(mission.getEnded(), missionCreateDTO.getEnded());
        Assert.assertEquals(mission.getLatitude(), missionCreateDTO.getLatitude());
        Assert.assertEquals(mission.getLongitude(), missionCreateDTO.getLongitude());
        Assert.assertEquals(mission.getMissionType(), missionCreateDTO.getMissionType());
    }

    @Test
    public void mapDepartmentToDepartmentDtoTest() {
        Department department = new Department();
        department.setId(1L);
        department.setCity("london");
        department.setCountry("uk");
        department.setLatitude(2.0);
        department.setLongitude(4.0);
        department.setSpecialization(DepartmentSpecialization.ASSASSINATION);

        DepartmentDTO departmentDTO = beanMappingService.mapTo(department, DepartmentDTO.class);

        Assert.assertEquals(department.getId(), departmentDTO.getId());
        Assert.assertEquals(department.getAgents().size(), departmentDTO.getAgentIds().size());
        Assert.assertEquals(department.getCity(), departmentDTO.getCity());
        Assert.assertEquals(department.getCountry(), departmentDTO.getCountry());
        Assert.assertEquals(department.getLatitude(), departmentDTO.getLatitude());
        Assert.assertEquals(department.getLongitude(), departmentDTO.getLongitude());
        Assert.assertEquals(department.getSpecialization(), departmentDTO.getSpecialization());
    }

    @Test
    public void mapDepartmentDtoToDepartmentTest() {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(1L);
        departmentDTO.setCity("london");
        departmentDTO.setCountry("uk");
        departmentDTO.setLatitude(2.0);
        departmentDTO.setLongitude(4.0);
        departmentDTO.setSpecialization(DepartmentSpecialization.ASSASSINATION);

        Department department = beanMappingService.mapTo(departmentDTO, Department.class);

        Assert.assertEquals(department.getId(), departmentDTO.getId());
        Assert.assertEquals(department.getAgents().size(), departmentDTO.getAgentIds().size());
        Assert.assertEquals(department.getCity(), departmentDTO.getCity());
        Assert.assertEquals(department.getCountry(), departmentDTO.getCountry());
        Assert.assertEquals(department.getLatitude(), departmentDTO.getLatitude());
        Assert.assertEquals(department.getLongitude(), departmentDTO.getLongitude());
        Assert.assertEquals(department.getSpecialization(), departmentDTO.getSpecialization());
    }

   @Test
   public void mapAgentToAgentDtoTest() {
        Agent agent = new Agent();
        agent.setId(1L);
        agent.setName("bond");
        agent.setLanguages(Collections.singleton(LanguageEnum.EN));
        agent.setCodeName("007");
        agent.setRank(AgentRankEnum.SENIOR);
        agent.setBirthDate(LocalDate.of(1996, 11, 8));

        AgentDTO agentDTO = beanMappingService.mapTo(agent, AgentDTO.class);

        Assert.assertEquals(agent.getId(), agentDTO.getId());
        Assert.assertEquals(agent.getName(), agentDTO.getName());
        Assert.assertEquals(agent.getCodeName(), agentDTO.getCodeName());
        Assert.assertEquals(agent.getRank(), agentDTO.getRank());
        Assert.assertEquals(agent.getBirthDate(), agentDTO.getBirthDate());
        Assert.assertEquals(agent.getLanguages(), agentDTO.getLanguages());
   }

    @Test
    public void mapAgentDtoToAgentTest() {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setId(1L);
        agentDTO.setName("bond");
        agentDTO.setLanguages(Collections.singleton(LanguageEnum.EN));
        agentDTO.setCodeName("007");
        agentDTO.setRank(AgentRankEnum.SENIOR);
        agentDTO.setBirthDate(LocalDate.of(1996, 11, 8));

        Agent agent = beanMappingService.mapTo(agentDTO, Agent.class);

        Assert.assertEquals(agent.getId(), agentDTO.getId());
        Assert.assertEquals(agent.getName(), agentDTO.getName());
        Assert.assertEquals(agent.getCodeName(), agentDTO.getCodeName());
        Assert.assertEquals(agent.getRank(), agentDTO.getRank());
        Assert.assertEquals(agent.getBirthDate(), agentDTO.getBirthDate());
        Assert.assertEquals(agent.getLanguages(), agentDTO.getLanguages());
    }

    @Test
    public void mapReportToReportDtoTest() {
        Report report = new Report();
        report.setId(42L);
        report.setText("report");
        report.setReportStatus(ReportStatus.APPROVED);
        report.setMissionResult(MissionResultReportEnum.COMPLETED);
        report.setDate(LocalDate.of(2001, 7, 3));

        ReportDTO reportDTO = beanMappingService.mapTo(report, ReportDTO.class);

        Assert.assertEquals(report.getId(), reportDTO.getId());
        Assert.assertEquals(report.getText(), reportDTO.getText());
        Assert.assertEquals(report.getReportStatus(), reportDTO.getReportStatus());
        Assert.assertEquals(report.getDate(), reportDTO.getDate());
        Assert.assertEquals(report.getMissionResult(), reportDTO.getMissionResult());
    }

    @Test
    public void mapReportDtoToReportTest() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setId(42L);
        reportDTO.setText("report");
        reportDTO.setReportStatus(ReportStatus.APPROVED);
        reportDTO.setMissionResult(MissionResultReportEnum.COMPLETED);
        reportDTO.setDate(LocalDate.of(2001, 7, 3));

        Report report = beanMappingService.mapTo(reportDTO, Report.class);

        Assert.assertEquals(report.getId(), reportDTO.getId());
        Assert.assertEquals(report.getText(), reportDTO.getText());
        Assert.assertEquals(report.getReportStatus(), reportDTO.getReportStatus());
        Assert.assertEquals(report.getDate(), reportDTO.getDate());
        Assert.assertEquals(report.getMissionResult(), reportDTO.getMissionResult());
    }

    @Test
    public void mapMissionToMissionDtoTest() {
        Mission mission = new Mission();
        mission.setId(142L);
        mission.setLatitude(3.4);
        mission.setLongitude(54.1);
        mission.setStarted(LocalDate.of(2013, 4, 9));
        mission.setEnded(LocalDate.of(2015, 4, 9));
        mission.setMissionType(MissionTypeEnum.SABOTAGE);

        MissionDTO missionDTO = beanMappingService.mapTo(mission, MissionDTO.class);

        Assert.assertEquals(mission.getId(), missionDTO.getId());
        Assert.assertEquals(mission.getLatitude(), missionDTO.getLatitude());
        Assert.assertEquals(mission.getLongitude(), missionDTO.getLongitude());
        Assert.assertEquals(mission.getStarted(), missionDTO.getStarted());
        Assert.assertEquals(mission.getEnded(), missionDTO.getEnded());
        Assert.assertEquals(mission.getMissionType(), missionDTO.getMissionType());
    }

    @Test
    public void mapMissionDtoToMissionTest() {
        MissionDTO missionDTO = new MissionDTO();
        missionDTO.setId(142L);
        missionDTO.setLatitude(3.4);
        missionDTO.setLongitude(54.1);
        missionDTO.setStarted(LocalDate.of(2013, 4, 9));
        missionDTO.setEnded(LocalDate.of(2015, 4, 9));
        missionDTO.setMissionType(MissionTypeEnum.SABOTAGE);

        Mission mission = beanMappingService.mapTo(missionDTO, Mission.class);

        Assert.assertEquals(mission.getId(), missionDTO.getId());
        Assert.assertEquals(mission.getLatitude(), missionDTO.getLatitude());
        Assert.assertEquals(mission.getLongitude(), missionDTO.getLongitude());
        Assert.assertEquals(mission.getStarted(), missionDTO.getStarted());
        Assert.assertEquals(mission.getEnded(), missionDTO.getEnded());
        Assert.assertEquals(mission.getMissionType(), missionDTO.getMissionType());
    }
}
