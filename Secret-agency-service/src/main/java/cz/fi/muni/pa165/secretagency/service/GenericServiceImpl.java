package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of generic service. It provides general method implementations.
 * Every other Service implementation should extend this one.
 *
 * This class needs specific dao as an second typed argument to be able to provide
 *    specific dao methods for services which extend this one.
 *
 * @param <Entity> Entity
 * @param <Dao> Concrete dao - it is used to make easier usage of generic dao in children - they don't need to
 *              cast dao.
 *
 * @author Adam Kral(433328), Jan Pavlu (487548)
 */
public abstract class GenericServiceImpl<Entity, Dao extends GenericDao<Entity>> implements GenericService<Entity> {

    private Dao dao;

    /**
     * This method should be used to access dao. It checks if dao is set and if it's not throw exception.
     *
     * @return dao for given service
     */
    @SuppressWarnings("WeakerAccess")
    protected Dao getDao() {
        if (dao == null) {
            throw new NullPointerException("Repository is not set");
        }
        return dao;
    }

    @Autowired
    public void setDao(Dao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public Entity save(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot save entity which value is null");
        }
        return getDao().save(entity);
    }

    @Override
    @Transactional
    public void delete(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot delete entity which value is null");
        }
        getDao().delete(entity);
    }

    @Override
    @Transactional
    public Entity merge(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot merge entity which value is null");
        }
        return getDao().merge(entity);
    }

    @Override
    @Transactional
    public void deleteEntityById(Long id) {
        if (id == null) {
            throw new NullPointerException("Entity with id set to null doesn't exist");
        }
        getDao().deleteEntityById(id);
    }

    @Override
    @Transactional
    public Entity getEntityById(Long id) {
        if (id == null) {
            throw new NullPointerException("Entity with id set to null doesn't exist");
        }
        return getDao().getEntityById(id);
    }

    @Override
    @Transactional
    public List<Entity> getAll() {
        return getDao().getAll();
    }
}
