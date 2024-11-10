package org.andersen.service.impl;

import org.andersen.config.StateConfig;
import org.andersen.exception.ApartmentNotFoundException;
import org.andersen.exception.InvalidClientNameException;
import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.andersen.service.ReservationService;

import java.util.Optional;

public class ReservationServiceImpl implements ReservationService {

    private final ApartmentServiceImpl apartmentService;

    public ReservationServiceImpl(ApartmentServiceImpl apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    public void reserveApartment(int id, String name) {
        if (StateConfig.isApartmentStatusChangeEnabled()) {
            Optional<Apartment> apartmentOptional = apartmentService.findApartmentById(id);

            if (apartmentOptional.isPresent()) {
                Apartment apartment = apartmentOptional.get();

                if (!apartment.getApartmentStatus().equals(ApartmentStatusEnum.FREE)) {
                    return;
                }

                if (name.isBlank()) {
                    throw new InvalidClientNameException("Invalid name");
                }

                apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
                apartment.setNameOfClient(name);

            } else {
                throw new ApartmentNotFoundException("No such apartment");
            }
        }
    }

    @Override
    public void releaseApartment(int id, String name) {
        if (StateConfig.isApartmentStatusChangeEnabled()) {
            Optional<Apartment> apartmentOptional = apartmentService.findApartmentById(id);

            if (apartmentOptional.isPresent()) {
                Apartment apartment = apartmentOptional.get();

                if (!apartment.getApartmentStatus().equals(ApartmentStatusEnum.RESERVED) || !apartment.getNameOfClient().equals(name)) {
                    return;
                }

                apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
                apartment.setNameOfClient("");
            }
        }
    }
}
