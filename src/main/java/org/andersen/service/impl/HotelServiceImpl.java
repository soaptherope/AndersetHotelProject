package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.model.dao.HotelDao;
import org.andersen.model.dao.impl.HotelDaoImpl;
import org.andersen.repository.StateRepository;
import org.andersen.service.ApartmentService;
import org.andersen.service.HotelService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HotelServiceImpl implements HotelService {

    private final ApartmentService apartmentService;

    private final StateRepository stateRepository = new StateRepository();

    private final HotelDao hotelDao = new HotelDaoImpl();


    public HotelServiceImpl() throws SQLException {
        this.apartmentService = new ApartmentServiceImpl();
    }

    @Override
    public void addApartment(Apartment apartment) throws IOException {
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

    @Override
    public void saveApartment(Hotel hotel) {
        hotelDao.save(hotel);
    }

    @Override
    public void updateApartment(Hotel hotel) {
        hotelDao.update(hotel);
    }

    @Override
    public void deleteApartment(Hotel hotel) {
        hotelDao.delete(hotel);
    }

    @Override
    public List<Hotel> findAllApartments() {
        return hotelDao.findAll();
    }
}
