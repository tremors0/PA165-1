package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Adam Skurla (487588)
 */
@ContextConfiguration(classes = SecretAgencyPersistenceApplicationContext.class)
public class AgentDaoTests extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AgentDao agentDao;

    @BeforeClass
    public void createAgents() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Department department = new Department();
        department.setCity("Dolne oresany");
        department.setCountry("SK");
        department.setLatitude(2d);
        department.setLongitude(1d);
        department.setSpecialization(DepartmentSpecialization.INTELLIGENCE);

        Agent agent1 = new Agent();
        agent1.setName("Janko Hrasko");
        agent1.setBirthDate(LocalDate.of(1980, 2, 2));
        agent1.setLanguages(new HashSet<>(Arrays.asList(LanguageEnum.EN, LanguageEnum.RU)));
        agent1.setRank(AgentRankEnum.SENIOR);
        agent1.setCodeName("Agent 1");
        agent1.setDepartment(department);

        department.addAgent(agent1);

        entityManager.persist(department);
        entityManager.persist(agent1);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void test01() {
    }
}
