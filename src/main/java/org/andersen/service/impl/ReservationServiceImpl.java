package org.andersen.service.impl;

import org.andersen.config.StateConfig;
import org.andersen.exception.InvalidClientNameException;
import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.andersen.repository.StateRepository;
import org.andersen.service.ReservationService;

import java.io.IOException;
import java.sql.SQLException;

public class ReservationServiceImpl implements ReservationService {

    private final ApartmentServiceImpl apartmentService;

    private final StateRepository stateRepository = new StateRepository();

    public ReservationServiceImpl(ApartmentServiceImpl apartmentService) throws SQLException {
        this.apartmentService = apartmentService;
    }

    @Override
    public void reserveApartment(int id, String name) {
        if (StateConfig.isApartmentStatusChangeEnabled()) {
            Apartment apartment = apartmentService.findById(id);

            if (!apartment.getApartmentStatus().equals(ApartmentStatusEnum.FREE)) {
                return;
            }

            if (name.isBlank()) {
                throw new InvalidClientNameException("Invalid name");
            }

            apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
            apartment.setNameOfClient(name);
            apartmentService.updateApartment(apartment);

            try {
                stateRepository.saveSerializedData(apartment);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void releaseApartment(int id, String name) {
        if (StateConfig.isApartmentStatusChangeEnabled()) {
            Apartment apartment = apartmentService.findById(id);


            if (!apartment.getApartmentStatus().equals(ApartmentStatusEnum.RESERVED) || !apartment.getNameOfClient().equals(name)) {
                return;
            }

            apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
            apartment.setNameOfClient("");
            apartmentService.updateApartment(apartment);

            try {
                stateRepository.saveSerializedData(apartment);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

