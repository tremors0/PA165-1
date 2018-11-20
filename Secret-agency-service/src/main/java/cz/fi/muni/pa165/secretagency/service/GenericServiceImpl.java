package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Adam Kral <433328>
 * Date: 11/19/18
 * Time: 11:10 PM
 */
@Transactional
public abstract class GenericServiceImpl<T> implements GenericService<T> {
    protected GenericDao<T> dao;

    protected GenericServiceImpl(GenericDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public T create(T t) {
        return this.dao.save(t);
    }

    @Override
    public void delete(T t) {
        this.dao.delete(t);
    }

    @Override
    public T update(T t) {
        return this.dao.merge(t);
    }

    @Override
    public T getById(Long id) {
        return this.dao.getEntityById(id);
    }

    @Override
    public List<T> getAll() {
        return this.dao.getAll();
    }
}
