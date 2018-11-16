package cz.fi.muni.pa165.secretagency.service.service;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Agent} entity.
 * @author (Adam Kral<433328>)
 */
interface AgentService {
    /**
     * Create new agent
     * @param agent agent to create
     */
    void createAgent(Agent agent);

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
    List<Agent> getAgentsByCodeName(String codename);

    /**
     * Assigns an agent to mission
     * @param agent agent to be assigned
     * @param mission mission to be assigned on
     */
    void assignAgentToMission(Agent agent, Mission mission);

    /**
     * Remove agent from a mission
     * @param agent agent to be removed
     * @param mission mission to be removed from
     */
    void unassignAgentFromMission(Agent agent, Mission mission);

    /**
     * Add agent to given department
     * Agent will be removed from current department
     * @param agent to be moved
     * @param department department to be moved to
     */
    void addAgentToDepartment(Agent agent, Department department);

    /**
     * Remove agent from department
     * @param agent agent to be removed
     * @param department department to be removed from
     */
    void removeAgentFromDepartment(Agent agent, Department department);
}
