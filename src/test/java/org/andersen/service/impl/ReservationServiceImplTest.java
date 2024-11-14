package org.andersen.service.impl;

import org.andersen.config.StateConfig;
import org.andersen.exception.ApartmentNotFoundException;
import org.andersen.exception.InvalidClientNameException;
import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    private Apartment apartment;

    @BeforeEach
    void setup() {
        apartment = new Apartment(100);
    }

    @Test
    void reserveApartment_WhenApartmentIsFree_AndStatusChangeEnabled_ReservesApartment() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void reserveApartment_WhenApartmentIsFree_AndStatusChangeDisabled_DoesNotChangeStatus() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(false);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void reserveApartment_WhenApartmentIsNotFree_DoesNotChangeStatus() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.reserveApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void releaseApartment_WhenApartmentIsReserved_AndStatusChangeEnabled_ReleasesApartment() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void releaseApartment_WhenApartmentIsReserved_AndStatusChangeDisabled_DoesNotChangeStatus() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(false);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void releaseApartment_WhenApartmentIsNotReserved_DoesNotChangeStatus() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.FREE);
        apartment.setNameOfClient("");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "Alisher");

        assertEquals(ApartmentStatusEnum.FREE, apartment.getApartmentStatus());
        assertEquals("", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void releaseApartment_WhenClientNameDoesNotMatch_DoesNotChangeStatus() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        apartment.setApartmentStatus(ApartmentStatusEnum.RESERVED);
        apartment.setNameOfClient("Alisher");
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));

        reservationService.releaseApartment(1, "NotAlisher");

        assertEquals(ApartmentStatusEnum.RESERVED, apartment.getApartmentStatus());
        assertEquals("Alisher", apartment.getNameOfClient());

        mockedStateConfig.close();
    }

    @Test
    void reserveApartment_WhenApartmentDoesNotExist_ThrowsException() {
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);
        when(apartmentServiceMock.findApartmentById(999)).thenReturn(Optional.empty());

        assertThrows(ApartmentNotFoundException.class, () ->
                reservationService.reserveApartment(999, "Alisher"));

        mockedStateConfig.close();
    }

    @Test
    void reserveApartment_WithEmptyClientName_ThrowsException() {
        when(apartmentServiceMock.findApartmentById(1)).thenReturn(Optional.of(apartment));
        final MockedStatic<StateConfig> mockedStateConfig = mockStatic(StateConfig.class);
        mockedStateConfig.when(StateConfig::isApartmentStatusChangeEnabled).thenReturn(true);

        assertThrows(InvalidClientNameException.class, () ->
                reservationService.reserveApartment(1, ""));

        mockedStateConfig.close();
    }
}
