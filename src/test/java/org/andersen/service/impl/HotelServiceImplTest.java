package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.Hotel;
import org.andersen.model.dao.impl.HotelDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

    @Mock
    private HotelDaoImpl hotelDao;

    @Mock
    private ApartmentServiceImpl apartmentService;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private Apartment apartment;

    @BeforeEach
    void setup() {
        apartment = new Apartment(50);
    }

    @Test
    void addApartment_whenHotelExists_shouldAddApartment() throws IOException {
        Hotel existingHotel = new Hotel("Andersen");
        when(hotelDao.findByName("Andersen")).thenReturn(Optional.of(existingHotel));

        doNothing().when(apartmentService).save(any(Apartment.class));

        hotelService.addApartment(apartment);

        verify(hotelDao, never()).save(any(Hotel.class));
        verify(apartmentService).save(apartment);
    }

    @Test
    void addApartment_whenHotelDoesNotExist_shouldCreateHotelAndAddApartment() throws IOException {
        when(hotelDao.findByName("Andersen")).thenReturn(Optional.empty());

        doNothing().when(hotelDao).save(any(Hotel.class));
        doNothing().when(apartmentService).save(any(Apartment.class));

        hotelService.addApartment(apartment);

        verify(hotelDao).save(any(Hotel.class));
        verify(apartmentService).save(apartment);
    }
}
