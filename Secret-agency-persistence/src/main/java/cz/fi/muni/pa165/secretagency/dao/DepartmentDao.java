package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Department;

import java.util.List;

/**
 * @author Jan Pavlu (487548)
 */
public interface DepartmentDao extends GenericDao<Department> {

    /**
     * \Returns number of employees in selected department.
     * @param departmentId selected department
     * @return number of employees in the department
     */
    int getNumberOfEmployees(Long departmentId);

    /**
     * Returns departments, which are located in selected country.
     * @param country selected country
     * @return departments, which are located in selected country
     */
    List<Department> getDepartmentsInCountry(String country);

    /**
     * Returns departments, which are located in selected city.
     * @param city selected city
     * @return departments, which are located in selected city
     */
    List<Department> getDepartmentsInCity(String city);

    // TODO popsat, az bude existovat DepartmentSpecializationResult
    List<Department> getDepartmentBySpecialization();
}
