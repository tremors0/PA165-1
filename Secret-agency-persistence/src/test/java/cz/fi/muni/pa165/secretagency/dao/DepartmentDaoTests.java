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
    private DepartmentDaoImpl departmentDao;

    private Department d1;

    @BeforeClass
    public  void createDepartments() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Department department = new Department();
        department.setCity("Ostrava");
        department.setCountry("CZ");
        department.setLatitude(2d);
        department.setLongitude(1d);
        department.setSpecialization(DepartmentSpecialization.ASSASSINATION);

        Department department2 = new Department();
        department2.setCity("Praha");
        department2.setCountry("USA");
        department2.setLatitude(4d);
        department2.setLongitude(5d);
        department2.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        em.persist(department2);

        Agent a1 = new Agent();
        a1.setDepartment(department);
        a1.setBirthDate(LocalDate.now());
        a1.setCodeName("Budha2");
        a1.addLanguage(LanguageEnum.CZ);
        a1.setRank(AgentRankEnum.JUNIOR);
        a1.setName("James Bond");

        em.persist(department);
        em.persist(a1);

        department.addAgent(a1);

        em.getTransaction().commit();
        em.close();

        this.d1 = department;
    }

    @Test
    public void getById() {
        Department found = departmentDao.getEntityById(this.d1.getId());
        assertEquals(found.getCity(), "Ostrava");
        assertEquals(found.getCountry(), "CZ");
        assertEquals(found.getLatitude(), 2d);
        assertEquals(found.getLongitude(), 1d);
        assertEquals(found.getSpecialization(), DepartmentSpecialization.ASSASSINATION);
        assertEquals(found.getAgents().size(), 1);
    }

//    @Test
////    public void getNumberOfEmployees() {
////        assertEquals(departmentDao.getNumberOfEmployees(d1.getId()), 2);
////    }

    @Test
    public void getDepartmentInCountry() {
        List<Department> found = departmentDao.getDepartmentsInCountry("CZ");
        assertEquals(found.size(), 1);
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
    }

    @Test
    public void getDepartmensBySpecializationNotFound() {
        List<Department> found = departmentDao.getDepartmentBySpecialization(DepartmentSpecialization.INTELLIGENCE);
        assertEquals(found.size(), 0);
    }
}
