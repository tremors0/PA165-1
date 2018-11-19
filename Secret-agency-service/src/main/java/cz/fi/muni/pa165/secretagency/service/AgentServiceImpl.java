package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.AgentDao;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.service.exceptions.AgentServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
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
        return getAll().stream().filter(agent -> agent.getMissions().contains(mission)).collect(Collectors.toList());
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
        if (agent.getMissions().contains(mission)) {
            throw new AgentServiceException("Cannot assign agent to mission, already assigned");
        }
        agent.addMission(mission);
    }

    @Override
    public void removeAgentFromMission(Agent agent, Mission mission) {
        if (!agent.getMissions().contains(mission)) {
            throw new AgentServiceException("Cannot remove agent from mission, could not find mission");
        }
        agent.removeMission(mission);
    }

    @Override
    public void addAgentToDepartment(Agent agent, Department department) {
        agent.setDepartment(department);
    }
}
