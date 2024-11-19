package org.andersen.model.dao;

import org.andersen.model.Apartment;

import java.util.List;

public interface ApartmentDao {

    Apartment findById(long id);

    void save(Apartment apartment);

    void update(Apartment apartment);

    void delete(Apartment apartment);

    List<Apartment> findAll();
}
