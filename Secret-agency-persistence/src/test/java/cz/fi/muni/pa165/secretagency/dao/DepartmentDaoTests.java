package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.entity.Agent;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.AgentRankEnum;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.enums.LanguageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/*
 * Tests for Department dao implementation
 */

/**
 * @author Adam Kral <433328>
 */
@ContextConfiguration(classes=SecretAgencyPersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class DepartmentDaoTests extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private DepartmentDao departmentDao;

    private Department d1;
    private Department d2;

    private Agent a1;
    private Agent a2;

    @BeforeClass
    public  void createDepartments() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        d1 = new Department();
        d1.setCity("Ostrava");
        d1.setCountry("CZ");
        d1.setLatitude(2d);
        d1.setLongitude(1d);
        d1.setSpecialization(DepartmentSpecialization.ASSASSINATION);

        d2 = new Department();
        d2.setCity("Praha");
        d2.setCountry("USA");
        d2.setLatitude(4d);
        d2.setLongitude(5d);
        d2.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        em.persist(d2);

        a1 = new Agent();
        a1.setDepartment(d1);
        a1.setBirthDate(LocalDate.now());
        a1.setCodeName("Budha2");
        a1.addLanguage(LanguageEnum.CZ);
        a1.setRank(AgentRankEnum.JUNIOR);
        a1.setName("James Bond");

        a2 = new Agent();
        a2.setDepartment(d1);
        a2.setBirthDate(LocalDate.of(1988, 11, 11));
        a2.setCodeName("Donald");
        a2.addLanguage(LanguageEnum.RU);
        a2.setRank(AgentRankEnum.SENIOR);
        a2.setName("Donald Trump");

        em.persist(d1);
        em.persist(a1);
        em.persist(a2);

        d1.addAgent(a1);
        d1.addAgent(a2);

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void getById() {
        Department found = departmentDao.getEntityById(this.d1.getId());
        assertEquals(found.getCity(), "Ostrava");
        assertEquals(found.getCountry(), "CZ");
        assertEquals(found.getLatitude(), 2d);
        assertEquals(found.getLongitude(), 1d);
        assertEquals(found.getSpecialization(), DepartmentSpecialization.ASSASSINATION);
        assertEquals(found.getAgents().size(), 2);

        Agent foundAgent1 = found.getAgents().get(0);
        assertEquals(foundAgent1, a1);

        Agent foundAgent2 = found.getAgents().get(1);
        assertEquals(foundAgent2, a2);
    }

    @Test
    public void getByIdNull() {
        assertNull(departmentDao.getEntityById(41312311L));
    }

    @Test
    public void getNumberOfEmployees() {
        assertEquals(departmentDao.getNumberOfEmployees(d1.getId()), 2L);
    }

    @Test
    public void getNumberOfEmployeesNoEmployees() {
        assertEquals(departmentDao.getNumberOfEmployees(d2.getId()), 0L);
    }

    @Test
    public void getDepartmentInCountry() {
        List<Department> found = departmentDao.getDepartmentsInCountry("CZ");
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), d1);
    }

    @Test
    public void getDepartmentsInCountryNotFound() {
        List<Department> found = departmentDao.getDepartmentsInCountry("LLOLLO");
        assertEquals(found.size(), 0);
    }

    @Test
    public void getDepartmentInCity() {
        List<Department> found = departmentDao.getDepartmentsInCity("Ostrava");
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), d1);
    }

    @Test
    public void getDepartmentsInCityNotFound() {
        List<Department> found = departmentDao.getDepartmentsInCity("LLOLLO");
        assertEquals(found.size(), 0);
    }

    @Test
    public void getDepartmensBySpecialization() {
        List<Department> found = departmentDao.getDepartmentBySpecialization(DepartmentSpecialization.ASSASSINATION);
        assertEquals(found.size(), 1);
        assertEquals(found.get(0), d1);
    }

    @Test
    public void getDepartmensBySpecializationNotFound() {
        List<Department> found = departmentDao.getDepartmentBySpecialization(DepartmentSpecialization.INTELLIGENCE);
        assertEquals(found.size(), 0);
    }
}
