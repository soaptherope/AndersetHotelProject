package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private Hotel hotel;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Apartment apartment;

    @BeforeEach
    void setup() throws IOException {
        apartment = new Apartment(50);
        when(hotel.getApartments()).thenReturn(new ArrayList<>());
        hotelService.addApartment(apartment);
    }

    @Test
    void addApartment() {
        assertEquals(1, hotel.getApartments().size());
        assertEquals(apartment, hotel.getApartments().get(0));
    }

    @Test
    void addMultipleApartments() throws IOException {
        Apartment apartmentTwo = new Apartment(100);

        hotelService.addApartment(apartmentTwo);

        assertEquals(2, hotel.getApartments().size());
        assertEquals(apartment, hotel.getApartments().get(0));
        assertEquals(apartmentTwo, hotel.getApartments().get(1));
    }
}
