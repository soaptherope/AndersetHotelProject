package org.andersen.service.impl;

import org.andersen.config.StateConfig;
import org.andersen.exception.InvalidClientNameException;
import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ApartmentServiceImpl apartmentServiceMock;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private MockedStatic<StateConfig> mockedStateConfig;

    private Apartment apartment;

    @BeforeEach
    void setup() {
        apartment = new Apartment(100);
        mockedStateConfig = mockStatic(StateConfig.class);
    }

    @AfterEach
    void tearDown() {
        mockedStateConfig.close();
    }

    @Test
    void reserveApartment_WhenApartmentIsFree_AndStatusChangeEnabled_ReservesApartment() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());
    }

    @Test
    void reserveApartment_WhenApartmentIsFree_AndStatusChangeDisabled_DoesNotChangeStatus() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(false);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void reserveApartment_WhenApartmentIsNotFree_DoesNotChangeStatus() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void releaseApartment_WhenApartmentIsReserved_AndStatusChangeEnabled_ReleasesApartment() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void releaseApartment_WhenApartmentIsReserved_AndStatusChangeDisabled_DoesNotChangeStatus() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(false);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());
    }

    @Test
    void releaseApartment_WhenApartmentIsNotReserved_DoesNotChangeStatus() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());
    }

    @Test
    void releaseApartment_WhenClientNameDoesNotMatch_DoesNotChangeStatus() {
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);

        reservationService.releaseApartment(1, "NotAlisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());
    }

    @Test
    void reserveApartment_WithEmptyClientName_ThrowsException() {
        when(apartmentServiceMock.findById(1)).thenReturn(apartment);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        assertThrows(InvalidClientNameException.class, () ->
                reservationService.reserveApartment(1, ""));
    }
}
