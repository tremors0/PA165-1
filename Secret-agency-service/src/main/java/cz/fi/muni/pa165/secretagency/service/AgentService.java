package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.service.exceptions.AgentServiceException;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Agent} entity.
 * @author (Adam Kral<433328>)
 */
interface AgentService {
    /**
     * Create new agent
     * @param agent to be created
     * @return created agent
     */
    Agent createAgent(Agent agent);

    /**
     * Get agent by his id
     * @param id agent's id
     * @return Agent with given id //TODO: throw exception on not found?
     */
    Agent getById(Long id);

    /**
     * Get all agents
     * @return all agents
     */
    List<Agent> getAll();

    /**
     * Delete given agent
     * @param agent to be deleted
     */
    void deleteAgent(Agent agent);

    /**
     * Get all agents on a given mission
     * @param mission mission
     * @return List of agents on a mission
     */
    List<Agent> getAgentsOnMission(Mission mission);

    /**
     * Get all agents by a given rank
     * @param rankEnum given rank
     * @return List of all agents with given rank
     */
    List<Agent> getAgentsByRank(AgentRankEnum rankEnum);

    /**
     * List of all agents with given code name
     * @param codename agent's codename
     * @return agents with given codename
     */
    Agent getAgentByCodeName(String codename);

    /**
     * Assigns an agent to mission
     * @param agent agent to be assigned
     * @param mission mission to be assigned on
     * @throws AgentServiceException when agent is already assigned to given mission
     */
    void assignAgentToMission(Agent agent, Mission mission) throws AgentServiceException;

    /**
     * Remove agent from a mission
     * @param agent agent to be removed
     * @param mission mission to be removed from
     * @throws AgentServiceException when given mission could not be found in a list of agent's missions
     */
    void removeAgentFromMission(Agent agent, Mission mission) throws AgentServiceException;

    /**
     * Add agent to given department
     * Agent will be removed from current department
     * @param agent to be moved
     * @param department department to be moved to
     */
    void addAgentToDepartment(Agent agent, Department department);
}
