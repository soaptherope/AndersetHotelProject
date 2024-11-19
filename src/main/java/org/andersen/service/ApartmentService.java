package org.andersen.service;

import org.andersen.model.Apartment;

import java.util.List;

public interface ApartmentService {

    List<Apartment> sortByStatus(int pageNumber, int pageSize);

    List<Apartment> sortById(int pageNumber, int pageSize);

    List<Apartment> sortByPrice(int pageNumber, int pageSize);

    List<Apartment> sortByNameOfClient(int pageNumber, int pageSize);

    Apartment findById(long id);

    void saveApartment(Apartment apartment);

    void updateApartment(Apartment apartment);

    void deleteApartment(Apartment apartment);

    List<Apartment> findAllApartments();
}
