package cz.fi.muni.pa165.secretagency.dao;

import cz.fi.muni.pa165.secretagency.entity.Department;
import org.springframework.stereotype.Repository;

/**
 * Department is chosen for test implementation, because it has no @NotNull relations with other entities.
 *
 * @author Jan Pavlu
 */
@Repository
public class GenericDaoTransactionalDepartment extends GenericDaoTransactional<Department>{
    /**
     * Constructor
     * Sets class to work with in CRUD operations
     */
    public GenericDaoTransactionalDepartment() {
        super(Department.class);
    }
}
