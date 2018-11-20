package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of generic service. It provides general method implementations.
 * Every other Service implementation should extend this one.
 *
 * @param <Entity> Entity
 *
 * 2author Jan Pavlu (487548)
 */
@Service
@Transactional
public class GenericServiceImpl<Entity> implements GenericService<Entity> {

    @Autowired
    private GenericDao<Entity> genericDao;

    @Override
    public Entity save(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot save entity which value is null");
        }
        return genericDao.save(entity);
    }

    @Override
    public void delete(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot delete entity which value is null");
        }
        genericDao.delete(entity);
    }

    @Override
    public Entity merge(Entity entity) {
        if (entity == null) {
            throw new NullPointerException("Cannot merge entity which value is null");
        }
        return genericDao.merge(entity);
    }

    @Override
    public void deleteEntityById(Long id) {
        if (id == null) {
            throw new NullPointerException("Entity with id set to null doesn't exist");
        }
        genericDao.deleteEntityById(id);
    }

    @Override
    public Entity getEntityById(Long id) {
        if (id == null) {
            throw new NullPointerException("Entity with id set to null doesn't exist");
        }
        return genericDao.getEntityById(id);
    }

    @Override
    public List<Entity> getAll() {
        return genericDao.getAll();
    }
}
