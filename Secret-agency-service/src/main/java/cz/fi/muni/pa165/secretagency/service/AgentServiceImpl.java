package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.AgentDao;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of the {@link AgentService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * Author: Adam Kral <433328>
 * Date: 11/16/18
 * Time: 2:54 PM
 */
@Service
public class AgentServiceImpl implements AgentService {

    @Inject
    private AgentDao agentDao;

    @Override
    public Agent createAgent(Agent agent) {
        return agentDao.save(agent);
    }

    @Override
    public Agent getById(Long id) {
        return agentDao.getEntityById(id);
    }

    @Override
    public List<Agent> getAll() {
        return agentDao.getAll();
    }

    @Override
    public void deleteAgent(Agent agent) {
        agentDao.deleteEntityById(agent.getId());
    }

    @Override
    public List<Agent> getAgentsOnMission(Mission mission) {
        throw new NotImplementedException();
    }

    @Override
    public List<Agent> getAgentsByRank(AgentRankEnum rankEnum) {
        return agentDao.getAgentsWithRank(rankEnum);
    }

    @Override
    public Agent getAgentByCodeName(String codename) {
        return agentDao.getAgentByCodename(codename);
    }

    @Override
    public void assignAgentToMission(Agent agent, Mission mission) {
        agent.addMission(mission);
    }

    @Override
    public void removeAgentFromMission(Agent agent, Mission mission) {
        agent.removeMission(mission);
    }

    @Override
    public void addAgentToDepartment(Agent agent, Department department) {
        agent.setDepartment(department);
    }

    @Override
    public void removeAgentFromDepartment(Agent agent, Department department) {
        agent.setDepartment(null);
    }
}
