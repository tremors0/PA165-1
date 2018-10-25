package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;

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

    /**
     * Returns departments with selected specialization.
     * @param departmentSpecialization selected specialization
     * @return departments with selected specialization
     */
    List<Department> getDepartmentBySpecialization(DepartmentSpecialization departmentSpecialization);
}
