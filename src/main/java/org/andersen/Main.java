package org.andersen;

import org.andersen.config.StateConfig;
import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.service.impl.ApartmentServiceImpl;
import org.andersen.service.impl.HotelServiceImpl;
import org.andersen.service.impl.ReservationServiceImpl;
import org.andersen.util.StateLoader;
import org.andersen.util.StateSaver;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        Hotel hotel = new Hotel();

        ApartmentServiceImpl apartmentService = new ApartmentServiceImpl(hotel);

        HotelServiceImpl hotelServiceImpl = new HotelServiceImpl(hotel);

        ReservationServiceImpl reservationService = new ReservationServiceImpl(apartmentService);

        String stateFilePath = StateConfig.getStateFilePath();

        hotelServiceImpl.addApartment(new Apartment(100));
        hotelServiceImpl.addApartment(new Apartment(150));
        hotelServiceImpl.addApartment(new Apartment(200));
        hotelServiceImpl.addApartment(new Apartment(250));

        StateSaver stateSaver = null;
        try {
            stateSaver = new StateSaver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateSaver.saveState(hotel, stateFilePath);

        System.out.println("All Apartments:");
        List<Apartment> allApartments = apartmentService.getAllApartments();
        allApartments.forEach(apartment -> System.out.println(apartment.toString()));

        System.out.println("\nSorted Apartments by Price:");
        List<Apartment> sortedByPrice = apartmentService.sortByPrice(1, 2);
        sortedByPrice.forEach(apartment -> System.out.println(apartment.toString()));


        System.out.println("\nReserving Apartment ID 1");
        reservationService.reserveApartment(1, "Alisher");

        Optional<Apartment> reservedApartment = apartmentService.findApartmentById(1);
        reservedApartment.ifPresent(System.out::println);

        System.out.println("\nAttempting to reserve Apartment ID 1 again");
        reservationService.reserveApartment(1, "Different Alisher");

        reservedApartment.ifPresent(System.out::println);

        System.out.println("\nReleasing Apartment ID 1 with incorrect name");
        reservationService.releaseApartment(1, "NotAlisher");

        reservedApartment.ifPresent(System.out::println);

        System.out.println("\nReleasing Apartment ID 1 with correct name");
        reservationService.releaseApartment(1, "Alisher");

        reservedApartment = apartmentService.findApartmentById(1);
        reservedApartment.ifPresent(System.out::println);

        StateLoader stateLoader;
        try {
            stateLoader = new StateLoader(stateFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Hotel loadedHotel = stateLoader.loadState(stateFilePath, Hotel.class);

        System.out.println(loadedHotel);

        System.out.println("\nAll Apartments After Operations:");
        allApartments.forEach(apartment -> System.out.println(apartment.toString()));
    }
}
