package org.andersen.service.impl;

import org.andersen.exception.ApartmentNotFoundException;
import org.andersen.exception.InvalidClientNameException;
import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTests {

    @Mock
    private ApartmentServiceImpl apartmentServiceMock;

    @InjectMocks
    private ReservationServiceImpl reservationService;
    
    private Apartment apartment;

    @BeforeEach
    void setup() {
        apartment = new Apartment(100);
    }

    @Test
    void testReserveApartment_WhenApartmentIsFree_ReservesApartment() {
        apartment.setStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getStatus());
        assertEquals("Alisher", apartment.getNameOfClient());
    }

    @Test
    void testReserveApartment_WhenApartmentIsNotFree_DoesNotChangeStatus() {
        apartment.setStatus(ApartmentStatusEnum.RESERVED);
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void testReleaseApartment_WhenApartmentIsReserved_ReleasesApartment() {
        apartment.setStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void testReleaseApartment_WhenApartmentIsNotReserved_DoesNotChangeStatus() {
        apartment.setStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void testReleaseApartment_WhenClientNameDoesNotMatch_DoesNotChangeStatus() {
        apartment.setStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "NotAlisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getStatus());
        assertEquals("Alisher", apartment.getNameOfClient());
    }

    @Test
    void testReserveApartment_WhenApartmentDoesNotExist_ThrowsException() {
        when(apartmentServiceMock.findApartmentById(999)).thenReturn(Optional.empty());

        assertThrows(ApartmentNotFoundException.class, () ->
                reservationService.reserveApartment(999, "Alisher"));
    }

    @Test
    void testReserveApartment_WithEmptyClientName_ThrowsException() {
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        assertThrows(InvalidClientNameException.class, () ->
                reservationService.reserveApartment(1, ""));
    }
}