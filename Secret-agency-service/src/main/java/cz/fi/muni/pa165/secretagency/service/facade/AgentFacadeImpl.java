package cz.fi.muni.pa165.secretagency.service.facade;

import cz.fi.muni.pa165.secretagency.dto.AgentCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.AgentDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentDTO;
import cz.fi.muni.pa165.secretagency.dto.MissionDTO;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;
import cz.fi.muni.pa165.secretagency.facade.AgentFacade;
import cz.fi.muni.pa165.secretagency.service.AgentService;
import cz.fi.muni.pa165.secretagency.service.BeanMappingService;
import cz.fi.muni.pa165.secretagency.service.DepartmentService;
import cz.fi.muni.pa165.secretagency.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Adam Kral <433328>
 * Date: 11/25/18
 * Time: 2:19 PM
 */
@Service
@Transactional
public class AgentFacadeImpl implements AgentFacade {
    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MissionService missionService;

    @Override
    public List<AgentDTO> getAllAgents() {
        return beanMappingService.mapTo(agentService.getAll(), AgentDTO.class);
    }

    @Override
    public AgentDTO getAgentById(Long id) {
        return beanMappingService.mapTo(agentService.getEntityById(id), AgentDTO.class);
    }

    @Override
    public Long createAgent(AgentCreateDTO agentCreateDTO) {
        Agent mappedAgent = beanMappingService.mapTo(agentCreateDTO, Agent.class);

        mappedAgent.setDepartment(departmentService.getEntityById(agentCreateDTO.getDepartmentId()));

        Agent savedAgent = agentService.save(mappedAgent);
        return savedAgent.getId();
    }

    @Override
    public void deleteAgent(Long id) {
        agentService.deleteEntityById(id);
    }

    @Override
    public List<AgentDTO> getAgentsByRank(AgentRankEnum rankEnum) {
        return beanMappingService.mapTo(agentService.getAgentsByRank(rankEnum), AgentDTO.class);
    }

    @Override
    public AgentDTO getAgentByCodeName(String codename) {
        return beanMappingService.mapTo(agentService.getAgentByCodeName(codename), AgentDTO.class);
    }

    @Override
    public void assignAgentToMission(AgentDTO agent, MissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(missionService.getEntityById(mission.getId()), Mission.class);
        Agent mappedAgent = beanMappingService.mapTo(agent, Agent.class);
        mappedAgent.addMission(mappedMission);
        agentService.save(mappedAgent);
    }

    @Override
    public void removeAgentFromMission(AgentDTO agent, MissionDTO mission) {
        Mission mappedMission = beanMappingService.mapTo(missionService.getEntityById(mission.getId()), Mission.class);
        Agent mappedAgent = beanMappingService.mapTo(agent, Agent.class);
        mappedAgent.removeMission(mappedMission);
        agentService.save(mappedAgent);
    }

    @Override
    public void addAgentToDepartment(AgentDTO agent, DepartmentDTO department) {
        Department mappedDepartment = beanMappingService.mapTo(departmentService.getEntityById(department.getId()), Department.class);
        Agent mappedAgent = beanMappingService.mapTo(agent, Agent.class);
        mappedAgent.setDepartment(mappedDepartment);
        agentService.save(mappedAgent);
    }

    @Override
    public List<AgentDTO> getAgentsWithLanguageKnowledge(LanguageEnum languageEnum) {
        return beanMappingService.mapTo(agentService.getAgentsWithLanguageKnowledge(languageEnum), AgentDTO.class);
    }

    @Override
    public List<AgentDTO> getSoonRetiringAgents() {
        return beanMappingService.mapTo(agentService.getSoonRetiringAgents(), AgentDTO.class);
    }
}
