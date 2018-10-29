package cz.fi.muni.pa165.secretagency.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class implemented only for generic dao testing. This class should never be used in real code.
 * Class is @Transactional to simulate Service layer.
 *
 * @author Jan Pavlu
 */
@Transactional
@Repository
public abstract class GenericDaoTransactional<T> extends GenericDaoImpl<T>{

    /**
     * Constructor
     * Sets class to work with in CRUD operations
     *
     * @param clazzToSet class of entity that should be worked with
     */
    protected GenericDaoTransactional(Class<T> clazzToSet) {
        super(clazzToSet);
    }

    @Override
    public T save(T t) {
        return super.save(t);
    }

    @Override
    public void delete(T t) {
        super.delete(t);
    }

    @Override
    public void deleteEntityById(Long id) {
        super.deleteEntityById(id);
    }

    @Override
    public T getEntityById(Long id) {
        return super.getEntityById(id);
    }

    @Override
    public List<T> getAll() {
        return super.getAll();
    }

    @Override
    public T merge(T t) {
        return super.merge(t);
    }
}
