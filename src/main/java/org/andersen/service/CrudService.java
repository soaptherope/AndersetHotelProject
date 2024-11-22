package org.andersen.service;

import java.util.List;

public interface CrudService<T> {

    T findById(long id);

    void save(T t);

    void update(T t);

    void delete(T t);

    List<T> findAll();
}
