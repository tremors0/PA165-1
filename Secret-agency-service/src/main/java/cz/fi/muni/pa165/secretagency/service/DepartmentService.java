package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import cz.fi.muni.pa165.secretagency.service.exceptions.DepartmentServiceException;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Department} entity.
 *
 * @author Milos Silhar
 */
public interface DepartmentService extends GenericService<Department> {

    /**
     * Gets all departments in given city
     * @param city city for which find all departments, can be null
     * @return Departments located in given city, or departments not located in any city if city is null
     */
    public List<Department> getDepartmentByCity(String city);

    /**
     * Gets all departments in given country.
     * @param country country for which find all departments
     * @return Departments located in given country
     * @throws NullPointerException if country is null
     */
    public List<Department> getDepartmentByCountry(String country);

    /**
     * Gets all departments with specialization
     * @param specialization specialization of departments
     * @return Departments with given specialization
     * @throws NullPointerException if specialization is null
     */
    public List<Department> getDepartmentsBySpecialization(DepartmentSpecialization specialization);

    /**
     * Changes specialization of given department
     * @param department Department to change specialization
     * @param newSpecialization new specialization of given department
     * @throws NullPointerException if department is null
     */
    public void changeSpecialization(Department department, DepartmentSpecialization newSpecialization);

    /**
     * Gets all departments in area around given point on the earth
     * @param originLatitude latitude of origin point
     * @param originLongitude longitude of origin point
     * @param maxDistance maximum distance from origin
     * @return Departments which are in 0 to maxDistance range from origin point
     * @throws NullPointerException if any parameter is null
     * @throws DepartmentServiceException when origin point has wrong coordinates or when maxDistance is negative
     */
    public List<Department> getDepartmentsInArea(Double originLatitude, Double originLongitude, Double maxDistance);

}
