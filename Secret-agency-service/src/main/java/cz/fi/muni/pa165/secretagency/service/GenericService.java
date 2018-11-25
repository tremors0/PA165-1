package cz.fi.muni.pa165.secretagency.service;

import java.util.List;

/**
 * Generic service which provides general operations over entities.
 * Every other Service class should extend this one.
 *
 * @param <Entity> Entity
 *
 * @author Adam Kral(433328), Jan Pavlu (487548)
 */
public interface GenericService<Entity> {
    /**
     * Persists entity into database.
     * @param entity Entity which is going to be persisted
     * @return Saved entity.
     * @throws NullPointerException when parameter is null
     */
    Entity save(Entity entity);

    /**
     * Deletes entity from database.
     * @param entity Entity which is going to be deleted.
     * @throws NullPointerException when parameter is null
     */
    void delete(Entity entity);

    /**
     * Merges detached entity
     * @param entity Detached entity
     * @return Entity, which is menaged now.
     * @throws NullPointerException when parameter is null
     */
    Entity merge(Entity entity);

    /**
     * Deletes entity from database.
     * @param id Id of entity which is going to be deleted.
     * @throws NullPointerException when parameter is null
     */
    void deleteEntityById(Long id);

    /**
     * Returns entity with given id. Returns null, when entity is not found.
     * @param id Id of entity.
     * @return Entity with given id.
     * @throws NullPointerException when parameter is null
     */
    Entity getEntityById(Long id);

    /**
     * Returns all entities. If table does not contain any entity, empty list is returned.
     * @return All entities from table.
     */
    List<Entity> getAll();
}
