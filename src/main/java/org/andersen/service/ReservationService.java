package org.andersen.service;

public interface ReservationService {

    void reserveApartment(int id, String name);

    void releaseApartment(int id, String name);
}
