package org.andersen.service.impl;

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
        Optional<Apartment> apartmentOptional = apartmentService.findApartmentById(id);

        if (apartmentOptional.isPresent()) {
            Apartment apartment = apartmentOptional.get();

            if (!apartment.getStatus().equals(ApartmentStatusEnum.FREE)) {
                return;
            }

            apartment.setStatus(ApartmentStatusEnum.RESERVED);
            apartment.setNameOfClient(name);
        }
    }

    @Override
    public void releaseApartment(int id, String name) {
        Optional<Apartment> apartmentOptional = apartmentService.findApartmentById(id);

        if (apartmentOptional.isPresent()) {
            Apartment apartment = apartmentOptional.get();

            if (!apartment.getStatus().equals(ApartmentStatusEnum.RESERVED) || !apartment.getNameOfClient().equals(name)) {
                return;
            }

            apartment.setStatus(ApartmentStatusEnum.FREE);
            apartment.setNameOfClient("");
        }
    }
}
