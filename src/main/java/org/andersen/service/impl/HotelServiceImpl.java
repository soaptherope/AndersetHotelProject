package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.model.dao.HotelDao;
import org.andersen.repository.StateRepository;
import org.andersen.service.ApartmentService;
import org.andersen.service.HotelService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class HotelServiceImpl implements HotelService {

    private final ApartmentService apartmentService;

    private final StateRepository stateRepository = new StateRepository();

    private final HotelDao hotelDao;


    public HotelServiceImpl(HotelDao hotelDao, ApartmentService apartmentService) throws SQLException {
        this.hotelDao = hotelDao;
        this.apartmentService = apartmentService;
    }

    @Override
    public void addApartment(Apartment apartment) throws IOException {
        Optional<Hotel> hotelOptional = hotelDao.findByName("Andersen");

        Hotel hotel = hotelOptional.orElseGet(() -> {
            Hotel newHotel = new Hotel("Andersen");
            hotelDao.save(newHotel);
            return newHotel;
        });

        apartment.setHotel(hotel);
        apartmentService.saveApartment(apartment);

        try {
            stateRepository.saveSerializedData(apartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hotel findById(long id) {
        return hotelDao.findById(id);
    }
}
