package org.andersen.service;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;

import java.io.IOException;

public interface HotelService {

    void addApartment(Apartment apartment) throws IOException;

    Hotel findById(long id);
}
