package cz.fi.muni.pa165.secretagency.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Generic DAO implementation. Every other DAO implementation class should extend this one.
 * Implements basic operations over entities.
 *
 * @author Jan Pavlu (487548)
 *
 * @param <T> Entity
 */
@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    protected Class<T> clazz;

    /**
     * Constructor
     * Sets class to work with in CRUD operations
     * @param clazzToSet class of entity that should be worked with
     */
    protected GenericDaoImpl(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @PersistenceContext
    protected EntityManager em;

    @Override
    public T save(T t) {
        em.persist(t);
        return t;
    }

    @Override
    public void delete(T t) {
        em.remove(em.contains(t) ? t : em.merge(t));
    }

    @Override
    public T merge(T t) {
        return em.merge(t);
    }

    @Override
    public void deleteEntityById(Long id) {
        T entityToRemove = this.getEntityById(id);
        if (entityToRemove == null) {
            throw new IllegalArgumentException("Entity with given ID doesn't exist");
        }
        this.delete(entityToRemove);
    }

    @Override
    public T getEntityById(Long id) {
        return em.find(clazz, id);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        return em.createQuery(query).getResultList();
    }
}
