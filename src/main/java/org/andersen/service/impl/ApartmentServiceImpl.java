package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.service.ApartmentService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ApartmentServiceImpl implements ApartmentService {

    private final Hotel hotel;

    public ApartmentServiceImpl(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public  List<Apartment> getAllApartments() {
        return hotel.getApartments();
    }

    @Override
    public Optional<Apartment> findApartmentById(int id) {
        for (Apartment apartment : hotel.getApartments()) {
            if (apartment.getId() == id) {
                return Optional.of(apartment);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Apartment> sortById(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = hotel.getApartments();
        sortedApartments.sort(Comparator.comparingInt(Apartment::getId));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByPrice(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = hotel.getApartments();
        sortedApartments.sort(Comparator.comparingDouble(Apartment::getPrice));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByNameOfClient(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = hotel.getApartments();
        sortedApartments.sort(Comparator.comparing(Apartment::getNameOfClient));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByStatus(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = hotel.getApartments();
        sortedApartments.sort(Comparator.comparing(Apartment::getStatus));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    private List<Apartment> getPaginatedList(List<Apartment> list, int pageNumber, int pageSize) {
        int totalItems = list.size();
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, totalItems);

        if (start >= totalItems || start < 0) {
            return new ArrayList<>();
        }

        return list.subList(start, end);
    }
}
