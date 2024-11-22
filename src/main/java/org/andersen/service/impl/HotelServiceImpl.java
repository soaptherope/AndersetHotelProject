package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.model.dao.impl.HotelDaoImpl;
import org.andersen.repository.StateRepository;
import org.andersen.service.CrudService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HotelServiceImpl implements CrudService<Hotel> {

    private final ApartmentServiceImpl apartmentService;

    private final StateRepository stateRepository = new StateRepository();

    private final HotelDaoImpl hotelDao;


    public HotelServiceImpl(HotelDaoImpl hotelDao, ApartmentServiceImpl apartmentService) throws SQLException {
        this.hotelDao = hotelDao;
        this.apartmentService = apartmentService;
    }

    public void addApartment(Apartment apartment) throws IOException {
        Optional<Hotel> hotelOptional = hotelDao.findByName("Andersen");

        Hotel hotel = hotelOptional.orElseGet(() -> {
            Hotel newHotel = new Hotel("Andersen");
            hotelDao.save(newHotel);
            return newHotel;
        });

        apartment.setHotel(hotel);
        apartmentService.save(apartment);

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

    @Override
    public void save(Hotel hotel) {
        hotelDao.save(hotel);
    }

    @Override
    public void update(Hotel hotel) {
        hotelDao.update(hotel);
    }

    @Override
    public void delete(Hotel hotel) {
        hotelDao.delete(hotel);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelDao.findAll();
    }
}
