package cz.fi.muni.pa165.secretagency.dao;

import java.util.List;

/**
 * Interface which describes common operations over entities.
 * Every other DAO interface should extend this one.
 *
 * @author Jan Pavlu (487548)
 *
 * @param <Entity> Entity
 */
public interface GenericDao<Entity> {

    /**
     * Persists entity into database.
     * @param entity Entity which is going to be persisted
     * @return Saved entity.
     */
    Entity save(Entity entity);

    /**
     * Deletes entity from database.
     * @param entity Entity which is going to be deleted.
     */
    void delete(Entity entity);

    /**
     * Merges detached entity
     * @param entity Detached entity
     * @return Entity, which is menaged now.
     */
    Entity merge(Entity entity);

    /**
     * Deletes entity from database.
     * @param id Id of entity which is going to be deleted.
     */
    void deleteEntityById(Long id);

    /**
     * Returns entity with given id.
     * @param id Id of entity.
     * @return Entity with given id.
     */
    Entity getEntityById(Long id);

    /**
     * Returns  all entities.
     * @return All entities from table.
     */
    List<Entity> getAll();
}
