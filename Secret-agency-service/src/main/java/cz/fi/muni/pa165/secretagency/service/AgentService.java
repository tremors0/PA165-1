package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;
import cz.fi.muni.pa165.secretagency.service.exceptions.AgentServiceException;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Agent} entity.
 * @author (Adam Kral<433328>)
 */
public interface AgentService extends GenericService<Agent> {
    /**
     * Get all agents by a given rank
     * @param rankEnum given rank
     * @return List of all agents with given rank
     * @throws NullPointerException when rank is null
     */
    List<Agent> getAgentsByRank(AgentRankEnum rankEnum);

    /**
     * List of all agents with given code name
     * @param codename agent's codename
     * @return agents with given codename
     * @throws NullPointerException when codename is null
     */
    Agent getAgentByCodeName(String codename);

    /**
     * Assigns an agent to mission
     * @param agent agent to be assigned
     * @param mission mission to be assigned on
     * @throws AgentServiceException when agent is already assigned to given mission
     * @throws NullPointerException when Agent or mission is null
     */
    void assignAgentToMission(Agent agent, Mission mission) throws AgentServiceException;

    /**
     * Remove agent from a mission
     * @param agent agent to be removed
     * @param mission mission to be removed from
     * @throws AgentServiceException when given mission could not be found in a list of agent's missions
     * @throws NullPointerException when agent or mission is null
     */
    void removeAgentFromMission(Agent agent, Mission mission) throws AgentServiceException;

    /**
     * Add agent to given department
     * Agent will be removed from current department
     * @param agent to be moved
     * @param department department to be moved to
     * @throws NullPointerException when agent or department is null
     */
    void addAgentToDepartment(Agent agent, Department department);

    /**
     * Get all agents who can speak given language
     * @param languageEnum in which agents can speak
     * @return all agents speaking given language
     * @throws NullPointerException when language is null
     */
    List<Agent> getAgentsWithLanguageKnowledge(LanguageEnum languageEnum);

    /**
     * Returns all agents which are soon to be retired
     * @return all soon retiring agents
     */
    List<Agent> getSoonRetiringAgents();
}
