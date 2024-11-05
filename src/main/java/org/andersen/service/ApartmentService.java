package org.andersen.service;

import org.andersen.model.Apartment;

import java.util.List;
import java.util.Optional;

public interface ApartmentService {

    List<Apartment> getAllApartments();

    Optional<Apartment> findApartmentById(int id);

    List<Apartment> sortByStatus(int pageNumber, int pageSize);

    List<Apartment> sortById(int pageNumber, int pageSize);

    List<Apartment> sortByPrice(int pageNumber, int pageSize);

    List<Apartment> sortByNameOfClient(int pageNumber, int pageSize);
}
