package org.andersen.service;

import org.andersen.model.Apartment;

import java.io.IOException;

public interface HotelService {

    void addApartment(Apartment apartment) throws IOException;
}
