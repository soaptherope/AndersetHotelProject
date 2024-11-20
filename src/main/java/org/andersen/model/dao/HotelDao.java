package org.andersen.model.dao;

import org.andersen.model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelDao {

    Hotel findById(long id);

    Optional<Hotel> findByName(String name);

    void save(Hotel hotel);

    void update(Hotel hotel);

    void delete(Hotel hotel);

    List<Hotel> findAll();
}
