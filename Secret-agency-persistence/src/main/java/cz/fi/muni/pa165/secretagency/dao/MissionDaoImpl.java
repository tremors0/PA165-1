package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.entity.Report;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class MissionDaoImpl extends GenericDaoImpl<Mission> implements MissionDao {
    /**
     * Constructor
     * Sets Mission class to work with in CRUD operations
     */
    public MissionDaoImpl() {
        super(Mission.class);
    }

    @Override
    public Collection<Agent> getAgentsOnMission(Mission mission) {
        TypedQuery<Agent> query = em.createQuery(
                "Select a from Agent a where a. = :userid",
                Mission.class);

        query.setParameter("userid", u);
        return query.getResultList();
    }

    @Override
    public void assignAgentToMission(Agent agent, Mission mission) {

    }

    @Override
    public void removeAgentFromMission(Agent agent, Mission mission) {

    }

    @Override
    public Collection<Report> getReportsFromMission(Mission mission) {
        return null;
    }
}
