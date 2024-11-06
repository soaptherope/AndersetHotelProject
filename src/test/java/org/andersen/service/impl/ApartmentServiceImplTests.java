package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.andersen.model.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceImplTests {

    @Mock
    private Hotel hotel;

    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    private List<Apartment> apartments;

    @BeforeEach
    void setUp() {
        apartments = new ArrayList<>();
        apartments.add(new Apartment(100));
        apartments.get(0).setId(1);
        apartments.add(new Apartment(200));
        apartments.get(1).setId(2);
        apartments.add(new Apartment(300));
        apartments.get(2).setId(3);

        when(hotel.getApartments()).thenReturn(apartments);
    }

    @Test
    void testGetAllApartments() {
        List<Apartment> result = apartmentService.getAllApartments();
        assertEquals(apartments, result);
    }

    @Test
    void testFindApartmentById_Found() {
        Optional<Apartment> result = apartmentService.findApartmentById(1);
        assertTrue(result.isPresent());
        assertEquals(apartments.get(0), result.get());
    }

    @Test
    void testFindApartmentById_NotFound() {
        Optional<Apartment> result = apartmentService.findApartmentById(999);
        assertFalse(result.isPresent());
    }

    @Test
    void testSortById() {
        List<Apartment> result = apartmentService.sortById(1, 10);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void testSortByPrice() {
        List<Apartment> result = apartmentService.sortByPrice(1, 10);
        assertEquals(3, result.size());
        assertEquals(100, result.get(0).getPrice());
    }

    @Test
    void testSortByNameOfClient() {
        apartments.get(1).setNameOfClient("Alisher");
        List<Apartment> result = apartmentService.sortByNameOfClient(1, 10);
        assertEquals(3, result.size());
        assertEquals("Alisher", result.get(2).getNameOfClient());
    }

    @Test
    void testSortByStatus() {
        List<Apartment> result = apartmentService.sortByStatus(1, 10);
        assertEquals(3, result.size());
        assertEquals(ApartmentStatusEnum.FREE, result.get(0).getStatus());
    }


}
