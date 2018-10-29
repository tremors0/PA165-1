package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.SecretAgencyPersistenceApplicationContext;
import cz.fi.muni.pa165.secretagency.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(classes = SecretAgencyPersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GenericDaoTests extends AbstractTestNGSpringContextTests {

    @Qualifier("genericDaoTransactionalDepartment")
    @Autowired
    private GenericDao<Department> genericDao;

    private Department brno;
    private Department washington;

    @BeforeMethod
    public void createDepartment() {
        brno = new Department();
        brno.setCity("Brno");
        brno.setCountry("Czech Republic");
        brno.setLatitude(25.0);
        brno.setLongitude(33.0);

        washington = new Department();
        washington.setCity("Washington");
        washington.setCountry("USA");
        washington.setLatitude(80.0);
        washington.setLongitude(10.0);
    }

    @Test
    public void saveEntity() {
        genericDao.save(brno);
        Department department = genericDao.getEntityById(brno.getId());
        Assert.assertEquals(department, brno);

        // delete saved entity - initial state
        genericDao.delete(brno);
    }

    @Test
    public void deleteEntityById() {
        genericDao.save(washington);
        Department department = genericDao.getEntityById(washington.getId());
        Assert.assertEquals(department, washington);
        genericDao.deleteEntityById(washington.getId());
        Department dep = genericDao.getEntityById(washington.getId());
        Assert.assertNull(dep);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void deleteEntityByIdNotFound() {
        genericDao.deleteEntityById(1L);
    }

    @Test
    public void deleteEntity() {
        genericDao.save(washington);
        Department department = genericDao.getEntityById(washington.getId());
        Assert.assertEquals(department, washington);
        genericDao.delete(washington);
        Department dep = genericDao.getEntityById(washington.getId());
        Assert.assertNull(dep);
    }

    @Test
    public void getEntityById() {
        genericDao.save(brno);
        Department department = genericDao.getEntityById(brno.getId());
        Assert.assertEquals(department, brno);

        // delete saved entity - initial state
        genericDao.delete(brno);
    }

    @Test
    public void getEntityByIdNotFound() {
        Department department = genericDao.getEntityById(2L);
        Assert.assertNull(department);
    }

    @Test
    public void getAll() {
        genericDao.save(brno);
        genericDao.save(washington);
        List<Department> departments = genericDao.getAll();
        Assert.assertEquals(departments.size(), 2);
        Assert.assertEquals(departments.get(0), brno);
        Assert.assertEquals(departments.get(1), washington);

        // delete saved entity - initial state
        genericDao.delete(brno);
        genericDao.delete(washington);
    }

    @Test
    public void getAllEmpty() {
        List<Department> departments = genericDao.getAll();
        Assert.assertEquals(departments.size(), 0);
    }
}
