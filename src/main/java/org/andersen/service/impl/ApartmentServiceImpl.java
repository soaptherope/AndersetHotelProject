package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.dao.ApartmentDao;
import org.andersen.model.dao.impl.ApartmentDaoImpl;
import org.andersen.service.ApartmentService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentDao apartmentDao = new ApartmentDaoImpl();

    @Override
    public List<Apartment> sortById(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = apartmentDao.findAll();
        sortedApartments.sort(Comparator.comparingLong(Apartment::getId));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByPrice(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = apartmentDao.findAll();
        sortedApartments.sort(Comparator.comparingDouble(Apartment::getPrice));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByNameOfClient(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = apartmentDao.findAll();
        sortedApartments.sort(Comparator.comparing(Apartment::getNameOfClient));

        return getPaginatedList(sortedApartments, pageNumber, pageSize);
    }

    @Override
    public List<Apartment> sortByStatus(int pageNumber, int pageSize) {
        List<Apartment> sortedApartments = apartmentDao.findAll();
        sortedApartments.sort(Comparator.comparing(Apartment::getApartmentStatus));

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

    @Override
    public Apartment findById(long id) {
        return apartmentDao.findById(id);
    }

    @Override
    public void saveApartment(Apartment apartment) {
        apartmentDao.save(apartment);
    }

    @Override
    public void updateApartment(Apartment apartment) {
        apartmentDao.update(apartment);
    }

    @Override
    public void deleteApartment(Apartment apartment) {
        apartmentDao.delete(apartment);
    }

    @Override
    public List<Apartment> findAllApartments() {
        return apartmentDao.findAll();
    }
}
