package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.DepartmentDao;
import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.service.exceptions.DepartmentServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

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
        if (department == null) {
            throw new NullPointerException("department is null");
        }
        if (newSpecialization == null) {
            throw new NullPointerException("newSpecialization is null");
        }
        department.setSpecialization(newSpecialization);
    }

    @Override
    public List<Department> getDepartmentsInArea(Double originLatitude, Double originLongitude, Double maxDistance) {
        if (originLatitude == null) {
            throw new NullPointerException("originLatitude is null");
        }
        if (originLongitude == null) {
            throw new NullPointerException("originLongitude is null");
        }
        if (maxDistance == null) {
            throw new NullPointerException("maxDistance is null");
        }
        if (originLatitude.compareTo(-90.0) < 0 || originLatitude.compareTo(90.0) > 0) {
            throw  new DepartmentServiceException("latitude is out of range [-90.0, 90.0]");
        }
        if (originLongitude.compareTo(-180.0) < 0 || originLongitude.compareTo(180.0) > 0) {
            throw  new DepartmentServiceException("longitude is out of range [-180.0, 180.0]");
        }
        if (maxDistance.compareTo(0.0) < 0) {
            throw new DepartmentServiceException("maxDistance is negative");
        }
        List<Department> allDepartments = getDao().getAll();
        List<Department> departmentsInArea = allDepartments.stream()
                .filter(d -> twoPointDistance(originLatitude, originLongitude, d.getLatitude(), d.getLongitude()).compareTo(maxDistance) <= 0)
                .collect(Collectors.toList());
        return departmentsInArea;
    }

    /**
     * Computes distance between two coordinate positions on earth. Result is in kilometers.
     */
    private Double twoPointDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double meanRadius = 6371; // mean radius of the Earth in kilometers
        double latitude1Radians = Math.toRadians(latitude1);
        double latitude2Radians = Math.toRadians(latitude2);
        double diffLatitude = Math.toRadians(latitude2 - latitude1);
        double diffLongitude = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(diffLatitude / 2.0) * Math.sin(diffLatitude / 2.0) +
                Math.cos(latitude1Radians) * Math.cos(latitude2Radians) *
                Math.sin(diffLongitude / 2.0) * Math.sin(diffLongitude / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt((1 - a)));
        return meanRadius * c;
    }
}
