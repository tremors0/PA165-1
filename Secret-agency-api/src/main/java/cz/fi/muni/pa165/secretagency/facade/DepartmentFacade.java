package cz.fi.muni.pa165.secretagency.facade;

import cz.fi.muni.pa165.secretagency.dto.DepartmentCreateDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentDTO;
import cz.fi.muni.pa165.secretagency.dto.DepartmentUpdateSpecializationDTO;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

import java.util.List;

public interface DepartmentFacade {

    /**
     * Gets all departments
     * @return all departments
     */
    public List<DepartmentDTO> getAllDepartments();

    /**
     * Gets department with given id
     * @param id id of department to find
     * @return department with given id
     */
    public DepartmentDTO getDepartmentById(Long id);

    /**
     * Creates new department
     * @return id of new department
     */
    public Long createDepartment(DepartmentCreateDTO departmentCreateDTO);

    /**
     * Changes specialization of department
     * @param departmentUpdateSpecializationDTO data about specialization change
     */
    public void changeSpecialization(DepartmentUpdateSpecializationDTO departmentUpdateSpecializationDTO);

    /**
     * Deletes department with diven id
     * @param departmentId id of department to deleted
     */
    public void deleteDepartment(Long departmentId);

    /**
     * Gets all departmens with given specialization
     * @param specialization specialization of departments to find
     * @return Departments with given specialization
     */
    public List<DepartmentDTO> getDepartmentsBySpecialization(DepartmentSpecialization specialization);

    /**
     * Gets all departments with given city
     * @param city city from where find departments
     * @return Departments from given city
     */
    public List<DepartmentDTO> getDepartmentsByCity(String city);

    /**
     * Gets all departments with given country
     * @param country country from where find departments
     * @return Departments from given country
     */
    public List<DepartmentDTO> getDepartmentsByCountry(String country);

    /**
     * Gets all departments located in 0 - maxDistance from origin point on the earth
     * @param latitude latitude of the origin point
     * @param longitude longitude of the origin point
     * @param maxDistance maximum distance from origin point
     * @return Departments located in 0 - maxDistance from origin point
     */
    public List<DepartmentDTO> getDepartmentsByArea(Double latitude, Double longitude, Double maxDistance);
}
