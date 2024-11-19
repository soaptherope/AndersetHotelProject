package org.andersen.service;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;

import java.io.IOException;
import java.util.List;

public interface HotelService {

    void addApartment(Apartment apartment) throws IOException;

    Hotel findById(long id);

    void saveApartment(Hotel hotel);

    void updateApartment(Hotel hotel);

    void deleteApartment(Hotel hotel);

    List<Hotel> findAllApartments();
}
