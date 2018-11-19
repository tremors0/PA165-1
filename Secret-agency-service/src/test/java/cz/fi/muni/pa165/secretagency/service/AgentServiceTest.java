package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.AgentDao;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


/**
 * Author: Adam Kral <433328>
 * Date: 11/16/18
 * Time: 4:01 PM
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class AgentServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private AgentDao agentDao;

    @Autowired
    @InjectMocks
    private AgentService agentService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test that all agents are returned
     */
    @Test
    public void getAllAgentsTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        when(agentDao.getAll()).thenReturn(Collections.singletonList(agent));
        assertEquals(1, agentService.getAll().size());
    }

    /**
     * Test that agent is created
     */
    @Test
    public void createAgentTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        agent.setCodeName("Rum");
        agent.setRank(AgentRankEnum.JUNIOR);
        when(agentDao.save(agent)).thenReturn(agent);
        Agent savedAgent = agentService.createAgent(agent);
        assertEquals(agent, savedAgent);
    }

    /**
     * Test that correct agent with is returned by id
     */
    @Test
    public void getByIdTest() {
        Agent agent1 = new Agent();
        agent1.setId(50L);
        when(agentDao.getEntityById(agent1.getId())).thenReturn(agent1);
        assertEquals(agent1, agentService.getById(agent1.getId()));
    }

    /**
     * Test that all agents with given mission are returned
     */
    @Test
    public void getAgentsOnMissionTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        Mission mission = new Mission();
        agent.addMission(mission);
        when(agentDao.getAll()).thenReturn(Collections.singletonList(agent));
        List<Agent> agentsOnMission = agentService.getAgentsOnMission(mission);
        assertEquals(1, agentsOnMission.size());
    }

    /**
     * Test that agents are not returned if they are assigned to no/other mission
     */
    @Test
    public void getAgentsOnMissionNoAgentsFoundTest() {
        Agent agentNoMission = new Agent();
        agentNoMission.setId(50L);
        Agent agentOnDifferentMission = new Agent();
        agentOnDifferentMission.setId(40L);
        Mission agentsMission = new Mission();
        agentsMission.setId(1L);
        agentOnDifferentMission.addMission(agentsMission);

        Mission assassination = new Mission();
        assassination.setId(2L);
        assassination.setMissionType(MissionTypeEnum.ASSASSINATION);

        List<Agent> agentsList = new ArrayList<>();
        agentsList.add(agentNoMission);
        agentsList.add(agentOnDifferentMission);

        when(agentDao.getAll()).thenReturn(agentsList);
        List<Agent> agentsOnMission = agentService.getAgentsOnMission(assassination);
        assertEquals(0, agentsOnMission.size());
    }

    /**
     * Assert that agent with given rank are returned
     */
    @Test
    public void getAgentsWithRankTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        agent.setRank(AgentRankEnum.JUNIOR);
        when(agentDao.getAgentsWithRank(AgentRankEnum.JUNIOR)).thenReturn(Collections.singletonList(agent));
        List<Agent> agentList = agentService.getAgentsByRank(AgentRankEnum.JUNIOR);
        assertEquals(1, agentList.size());
        assertEquals(50L, (long) agentList.get(0).getId());
    }


    /**
     * Assert that agent with given codename is returned
     */
    @Test
    public void getAgentByCodenameTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        agent.setCodeName("Tequila");
        when(agentDao.getAgentByCodename("Tequila")).thenReturn(agent);
        Agent foundAgent = agentService.getAgentByCodeName("Tequila");
        assertEquals(50L, (long) foundAgent.getId());
    }

    /**
     * Assert that agent is assigned to given mission
     */
    @Test
    public void assignAgentToMissionTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        Mission mission = new Mission();
        mission.setId(1L);
        agentService.assignAgentToMission(agent, mission);
        assertEquals(1, agent.getMissions().size());
        assertEquals(1L, (long) agent.getMissions().get(0).getId());
    }

    /**
     * Assert that agent is removed from given mission
     */
    @Test
    public void removeAgentFromMissionTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        Mission mission = new Mission();
        mission.setId(1L);
        agent.addMission(mission);
        agentService.removeAgentFromMission(agent, mission);
        assertEquals(0, agent.getMissions().size());
    }

    /**
     * Assert that agent department is correctly changed
     */
    @Test
    public void addAgentToDepartmentTest() {
        Agent agent = new Agent();
        agent.setId(50L);
        Department formerDepartment = new Department();
        formerDepartment.setId(1L);

        Department newDepartment = new Department();
        newDepartment.setId(2L);

        agent.setDepartment(formerDepartment);

        agentService.addAgentToDepartment(agent, newDepartment);
        assertEquals(2L, (long) agent.getDepartment().getId());
    }
}