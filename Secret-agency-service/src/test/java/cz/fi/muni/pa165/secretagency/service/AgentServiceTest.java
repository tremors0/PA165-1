package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.AgentDao;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
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

    @Test
    public void getAgentsOnMissionTest() {
        Agent agent = new Agent(50L);
        Mission mission = new Mission();
        agent.addMission(mission);
    }

    @Test
    public void getAgentsWithRankTest() {
        Agent agent = new Agent(50L);
        agent.setRank(AgentRankEnum.JUNIOR);
        when(agentDao.getAgentsWithRank(AgentRankEnum.JUNIOR)).thenReturn(Collections.singletonList(agent));
        List<Agent> agentList = agentService.getAgentsByRank(AgentRankEnum.JUNIOR);
        assertEquals(1, agentList.size());
        assertEquals(50L, (long) agentList.get(0).getId());
    }


    @Test
    public void getAgentByCodenameTest() {
        Agent agent = new Agent(50L);
        agent.setCodeName("Tequila");
        when(agentDao.getAgentByCodename("Tequila")).thenReturn(agent);
        Agent foundAgent = agentService.getAgentByCodeName("Tequilla");
        assertEquals(50L, (long) agent.getId());
    }

    @Test
    public void assignAgentToMissionTest() {
        Agent agent = new Agent(50L);
        Mission mission = new Mission();
        mission.setId(1L);
        agentService.assignAgentToMission(agent, mission);
        assertEquals(1, agent.getMissions().size());
        assertEquals(1L, (long) agent.getMissions().get(0).getId());
    }

    @Test
    public void removeAgentFromMissionTest() {
        Agent agent = new Agent(50L);
        Mission mission = new Mission();
        mission.setId(1L);
        agent.addMission(mission);
        agentService.removeAgentFromMission(agent, mission);
        assertEquals(0, agent.getMissions().size());
    }

    @Test
    public void addAgentToDepartmentTest() {
        Agent agent = new Agent(50L);
        Department formerDepartment = new Department();
        formerDepartment.setId(1L);

        Department newDepartment = new Department();
        newDepartment.setId(2L);

        agent.setDepartment(formerDepartment);

        agentService.addAgentToDepartment(agent, newDepartment);
        assertEquals(2L, (long) agent.getDepartment().getId());
    }
}