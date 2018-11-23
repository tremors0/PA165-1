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
 * @param <Entity> Entity
 */
@Repository
public abstract class GenericDaoImpl<Entity> implements GenericDao<Entity> {

    protected Class<Entity> clazz;

    /**
     * Constructor
     * Sets class to work with in CRUD operations
     * @param clazzToSet class of entity that should be worked with
     */
    protected GenericDaoImpl(Class<Entity> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Entity save(Entity entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public void delete(Entity entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public Entity merge(Entity entity) {
        return em.merge(entity);
    }

    @Override
    public void deleteEntityById(Long id) {
        Entity entityToRemove = this.getEntityById(id);
        if (entityToRemove == null) {
            throw new IllegalArgumentException("Entity with given ID doesn't exist");
        }
        this.delete(entityToRemove);
    }

    @Override
    public Entity getEntityById(Long id) {
        return em.find(clazz, id);
    }

    @Override
    public List<Entity> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Entity> query = cb.createQuery(clazz);
        Root<Entity> root = query.from(clazz);
        query.select(root);
        return em.createQuery(query).getResultList();
    }
}
