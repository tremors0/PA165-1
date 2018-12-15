package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.DepartmentDao;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import cz.fi.muni.pa165.secretagency.service.exceptions.DepartmentServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Tests for department service class.
 *
 * @author Milos Silhar
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DepartmentServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentService departmentService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(departmentService, "dao", departmentDao);
    }

    @Test
    public void getDepartmentByIdOK() {
        Department department = new Department();
        department.setId(1L);
        department.setCountry("country");
        when(departmentDao.getEntityById(department.getId())).thenReturn(department);
        Department result = departmentService.getEntityById(1L);
        Assert.assertEquals(result.getId(), department.getId());
        Assert.assertEquals(result.getCountry(), department.getCountry());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getDepartmentByIdNull() {
        departmentService.getEntityById(null);
    }

    @Test
    public void getAllDepartments() {
        Department department = new Department();
        department.setId(1L);
        when(departmentDao.getAll()).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getAll();
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getId(), department.getId());
    }

    @Test
    public void getDepartmentByCityOK() {
        Department department = new Department();
        department.setId(1L);
        department.setCity("city");
        when(departmentDao.getDepartmentsInCity(department.getCity())).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentByCity(department.getCity());
        Assert.assertEquals(result.size(), 1L);
        Assert.assertEquals(result.get(0).getId(), department.getId());
        Assert.assertEquals(result.get(0).getCity(), department.getCity());
    }

    @Test
    public void getDepartmentByCityNull() {
        Department department = new Department();
        department.setId(1L);
        department.setCity(null);
        when(departmentDao.getDepartmentsInCity(department.getCity())).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentByCity(department.getCity());
        Assert.assertEquals(result.size(), 1L);
        Assert.assertEquals(result.get(0).getId(), department.getId());
        Assert.assertEquals(result.get(0).getCity(), department.getCity());
    }

    @Test
    public void getDepartmentByCountryOK() {
        Department department = new Department();
        department.setId(1L);
        department.setCountry("country");
        when(departmentDao.getDepartmentsInCountry(department.getCountry())).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentByCountry(department.getCountry());
        Assert.assertEquals(result.size(), 1L);
        Assert.assertEquals(result.get(0).getId(), department.getId());
        Assert.assertEquals(result.get(0).getCountry(), department.getCountry());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void getDepartmentByCountryNull() {
        departmentService.getDepartmentByCountry(null);
    }

    @Test
    public void getDepartmentBySpecializationOK() {
        Department department = new Department();
        department.setId(1L);
        department.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        when(departmentDao.getDepartmentBySpecialization(department.getSpecialization())).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentsBySpecialization(department.getSpecialization());
        Assert.assertEquals(result.size(), 1L);
        Assert.assertEquals(result.get(0).getId(), department.getId());
        Assert.assertEquals(result.get(0).getSpecialization(), department.getSpecialization());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void getDepartmentBySpecializationNull() {
        departmentService.getDepartmentsBySpecialization(null);
    }

    @Test
    public void changeSpecializationOK() {
        Department department = new Department();
        department.setId(1L);
        department.setSpecialization(DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
        Assert.assertEquals(department.getSpecialization(), DepartmentSpecialization.INTERNATIONAL_RELATIONSHIP);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void changeSpecializationDepartmentNull() {
        departmentService.updateDepartment(null);
    }

    @Test
    public void getDepartmentsInAreaInside() {
        Department department = new Department();
        department.setId(1L);
        department.setLatitude(2.0d);
        department.setLongitude(1.0d);
        when(departmentDao.getAll()).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentsInArea(0.0d, 0.0d, 250.0d);
        Assert.assertEquals(result.size(), 1L);
        Assert.assertEquals(result.get(0).getId(), department.getId());
    }

    @Test
    public void getDepartmentsInAreaOutside() {
        Department department = new Department();
        department.setId(1L);
        department.setLatitude(2.0d);
        department.setLongitude(2.0d);
        when(departmentDao.getAll()).thenReturn(Collections.singletonList(department));
        List<Department> result = departmentService.getDepartmentsInArea(0.0d, 0.0d, 300.0d);
        Assert.assertEquals(result.size(), 0L);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void getDepartmentsInAreaNullDistance() {
        departmentService.getDepartmentsInArea(0.0d, 0.0d, null);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void getDepartmentsInAreaNullLatitude() {
        departmentService.getDepartmentsInArea(null, 0.0d, 10d);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void getDepartmentsInAreaNullLongitude() {
        departmentService.getDepartmentsInArea(0.0d, null, 10d);
    }

    @Test (expectedExceptions = DepartmentServiceException.class)
    public void getDepartmentsInAreaNegativeDistance() {
        departmentService.getDepartmentsInArea(0.0d, 0.0d, -0.5d);
    }

    @Test (expectedExceptions = DepartmentServiceException.class)
    public void getDepartmentsInAreaNegativeWrongLatitude() {
        departmentService.getDepartmentsInArea(-90.1d, 0.0d, 90d);
    }

    @Test (expectedExceptions = DepartmentServiceException.class)
    public void getDepartmentsInAreaPositiveWrongLatitude() {
        departmentService.getDepartmentsInArea(90.1d, 0.0d, 90d);
    }

    @Test (expectedExceptions = DepartmentServiceException.class)
    public void getDepartmentsInAreaNegativeWrongLongitude() {
        departmentService.getDepartmentsInArea(0.0d, -180.1, 90d);
    }

    @Test (expectedExceptions = DepartmentServiceException.class)
    public void getDepartmentsInAreaPositiveWrongLongitude() {
        departmentService.getDepartmentsInArea(0.0d, 180.1, 90d);
    }
}
