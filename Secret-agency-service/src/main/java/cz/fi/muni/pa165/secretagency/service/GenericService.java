package cz.fi.muni.pa165.secretagency.service;

import java.util.List;

/**
 * Interface describing common service behavior
 * All other services should implement this interface
 *
 * Author: Adam Kral <433328>
 * Date: 11/19/18
 * Time: 10:26 PM
 */
public interface GenericService<T> {
    /**
     * Create given entity
     * @param t entity to be created
     * @return create entity
     */
    T create(T t);

    /**
     * Delete given entity
     * @param t deleted entity
     */
    void delete(T t);

    /**
     * Update given entity
     * @param t entity to be updated
     * @return updated entity
     */
    T update(T t);

    /**
     * Get entity by id
     * @param id of entity
     * @return entity with give ID
     */
    T getById(Long id);

    /**
     * Get all entities
     * @return list of all entities
     */
    List<T> getAll();
}
