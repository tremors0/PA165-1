package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Department;
import cz.fi.muni.pa165.secretagency.enums.DepartmentSpecialization;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of DAO for the Department entity.
 *
 * @author Jan Pavlu(487548)
 */
@Repository
public class DepartmentDaoImpl extends GenericDaoImpl<Department> implements DepartmentDao {

    /**
     * Constructor
     * Sets class to work with in CRUD operations
     */
    public DepartmentDaoImpl() {
        super(Department.class);
    }

    @Override
    public long getNumberOfEmployees(Long departmentId) {
        Object result = em.createQuery("SELECT COUNT(d) FROM Department d " +
                "inner JOIN d.agents " +
                "WHERE d.id = :departmentId")
                .setParameter("departmentId", departmentId)
                .getSingleResult();
        return (long)result;
    }

    @Override
    public List<Department> getDepartmentsInCountry(String country) {
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d " +
                "WHERE d.country = :country ", Department.class);
        return query.setParameter("country", country)
                .getResultList();
    }

    @Override
    public List<Department> getDepartmentsInCity(String city) {
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d " +
                "WHERE d.city = :city ", Department.class);
        return query.setParameter("city", city)
                .getResultList();
    }

    @Override
    public List<Department> getDepartmentBySpecialization(DepartmentSpecialization specialization) {
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d " +
                "WHERE d.specialization = :specialization", Department.class);
        return query.setParameter("specialization", specialization)
                .getResultList();
    }
}
