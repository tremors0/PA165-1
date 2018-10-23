package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;

import java.util.Collection;

/**
 * @author Jan Pavlu (487548)
 */
public interface DepartmentDao extends GenericDao<Department> {

    /**
     * Returns all agents in the department.
     * @param department selected department
     * @return all agents in given department.
     */
    Collection<Agent> getAgentsInDepartment(Department department);

    /**
     * Add agent to the department.
     * @param department selected department.
     * @param agent agent, which should be added to the department.
     * @return <code>true</code>, if agent was added, otherwise <code>false</code>
     */
    boolean addAgentToDepartment(Department department, Agent agent);

    /**
     * Remove agent from the department.
     * @param department selected department
     * @param agent agent, which should be removed
     * @return <code>true</code>, if agent was removed, otherwise <code>false</code>
     */
    boolean removeAgentFromDepartment(Department department, Agent agent);
}
