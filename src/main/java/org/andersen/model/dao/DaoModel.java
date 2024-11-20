package org.andersen.model.dao;

import java.util.List;

public interface DaoModel<T> {

    T findById(long id);

    void save(T t);

    void update(T t);

    void delete(T t);

    List<T> findAll();
}
