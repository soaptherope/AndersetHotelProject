package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.repository.StateRepository;
import org.andersen.service.HotelService;

import java.io.IOException;
import java.sql.SQLException;

public class HotelServiceImpl implements HotelService {

    private final Hotel hotel;

    private final StateRepository stateRepository = new StateRepository();


    public HotelServiceImpl(Hotel hotel) throws SQLException {
        this.hotel = hotel;
    }

    @Override
    public void addApartment(Apartment apartment) throws IOException {
        hotel.getApartments().add(apartment);

        try {
            stateRepository.saveSerializedData(apartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
