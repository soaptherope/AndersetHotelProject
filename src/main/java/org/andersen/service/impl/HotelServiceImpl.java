package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.service.HotelService;

public class HotelServiceImpl implements HotelService {

    private final Hotel hotel;

    public HotelServiceImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void addApartment(Apartment apartment) {
        hotel.getApartments().add(apartment);
    }
}
