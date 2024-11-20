package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.ApartmentStatusEnum;
import org.andersen.model.dao.ApartmentDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceImplTest {

    @Mock
    private ApartmentDao apartmentDao;

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

        when(apartmentDao.findAll()).thenReturn(apartments);
    }

    @Test
    void getAllApartments() {
        List<Apartment> result = apartmentService.findAllApartments();
        assertEquals(apartments, result);
    }

    @Test
    void sortById() {
        List<Apartment> result = apartmentService.sortById(1, 10);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void sortByPrice() {
        List<Apartment> result = apartmentService.sortByPrice(1, 10);
        assertEquals(3, result.size());
        assertEquals(100, result.get(0).getPrice());
    }

    @Test
    void sortByNameOfClient() {
        apartments.get(1).setNameOfClient("Alisher");
        List<Apartment> result = apartmentService.sortByNameOfClient(1, 10);
        assertEquals(3, result.size());
        assertEquals("Alisher", result.get(2).getNameOfClient());
    }

    @Test
    void sortByStatus() {
        List<Apartment> result = apartmentService.sortByStatus(1, 10);
        assertEquals(3, result.size());
        assertEquals(ApartmentStatusEnum.FREE, result.get(0).getApartmentStatus());
    }


}
