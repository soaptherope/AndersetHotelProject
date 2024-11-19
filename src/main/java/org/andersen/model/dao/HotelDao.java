package org.andersen.model.dao;

import org.andersen.model.Hotel;

import java.util.List;

public interface HotelDao {

    Hotel findById(long id);

    void save(Hotel hotel);

    void update(Hotel hotel);

    void delete(Hotel hotel);

    List<Hotel> findAll();
}
