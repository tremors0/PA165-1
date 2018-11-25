package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.DepartmentDao;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of service access to the {@link Department} entity.
 * This class is part of the service module of the application
 * that provides the implementation of the business logic (main logic of the application).
 *
 * @author Milos Silhar (433614)
 */
@Service
@Transactional
public class DepartmentServiceImpl extends GenericServiceImpl<Department, DepartmentDao> implements DepartmentService {

    @Override
    public List<Department> getDepartmentByCity(String city) {
        return getDao().getDepartmentsInCity(city);
    }

    @Override
    public List<Department> getDepartmentByCountry(String country) {
        if (country == null) {
            throw new NullPointerException("country is null");
        }
        return getDao().getDepartmentsInCountry(country);
    }

    @Override
    public List<Department> getDepartmentsBySpecialization(DepartmentSpecialization specialization) {
        if (specialization == null) {
            throw new NullPointerException("specialization is null");
        }
        return getDao().getDepartmentBySpecialization(specialization);
    }

    @Override
    public void changeSpecialization(Department department, DepartmentSpecialization newSpecialization) {
        department.setSpecialization(newSpecialization);
    }

    @Override
    public List<Department> getDepartmentsInArea(Double originLatitude, Double originLongitude, Double maxDistance) {
        return null;
    }
}
