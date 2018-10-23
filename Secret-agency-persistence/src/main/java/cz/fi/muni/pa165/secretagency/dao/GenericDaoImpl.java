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

    @PersistenceContext
    private EntityManager em;

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
    public void deleteEntityById(Long id, Class<T> entityClazz) {
        T entityToRemove = em.find(entityClazz, id);
        em.remove(entityToRemove);
    }

    @Override
    public T getEntityById(Long id, Class<T> entityClazz) {
        return em.find(entityClazz, id);
    }

    @Override
    public List<T> getAll(Class<T> entityClazz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClazz);
        Root<T> root = query.from(entityClazz);
        query.select(root);
        return em.createQuery(query).getResultList();
    }
}
