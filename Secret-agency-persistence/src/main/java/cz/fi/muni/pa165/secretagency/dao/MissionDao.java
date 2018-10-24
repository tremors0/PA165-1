package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;

import java.util.Collection;

/**
 * @author Adam Kral (433328)
 */
public interface MissionDao extends GenericDao<Mission> {
    /**
     * Get all agents on a given mission
     * @param mission from which agents are selected
     * @return all agents in given mission
     */
    Collection<Agent> getAgentsOnMission(Mission mission);

    /**
     * Assigns agent to a mission
     * @param agent to be assigned
     * @param mission on which the agent should be assigned
     */
    void assignAgentToMission(Agent agent, Mission mission);

    /**
     * Removes agent from a mission
     * @param agent to be removed from mission
     * @param mission from which agent should be removed
     */
    void removeAgentFromMission(Agent agent, Mission mission);

    /**
     * Returns all reports of a mission
     * @param mission mission from which the reports should be returned
     * @return all reports of given mission
     */
    Collection<Report> getReportsFromMission(Mission mission);
}
