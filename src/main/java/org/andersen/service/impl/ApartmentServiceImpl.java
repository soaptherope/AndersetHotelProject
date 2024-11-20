package org.andersen.service.impl;

import org.andersen.model.Apartment;
import org.andersen.model.dao.impl.ApartmentDaoImpl;
import org.andersen.service.CrudService;
import org.andersen.service.SortService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ApartmentServiceImpl implements SortService<Apartment>, CrudService<Apartment> {

    private final ApartmentDaoImpl apartmentDao;

    public ApartmentServiceImpl(ApartmentDaoImpl apartmentDao) {
        this.apartmentDao = apartmentDao;
    }

    @Override
    public List<Apartment> sortByField(int pageNumber, int pageSize, String fieldName) {
        List<Apartment> sortedApartments = apartmentDao.findAll();

        Map<String, Function<Apartment, Comparable>> sortFields = Map.of(
                "id", Apartment::getId,
                "price", Apartment::getPrice,
                "nameOfClient", Apartment::getNameOfClient,
                "status", Apartment::getApartmentStatus
        );

        Function<Apartment, Comparable> fieldGetter = sortFields.get(fieldName);
        if (fieldGetter != null) {
            sortedApartments.sort(Comparator.comparing(fieldGetter));
        }

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
    public void save(Apartment apartment) {
        apartmentDao.save(apartment);
    }

    @Override
    public void update(Apartment apartment) {
        apartmentDao.update(apartment);
    }

    @Override
    public void delete(Apartment apartment) {
        apartmentDao.delete(apartment);
    }

    @Override
    public List<Apartment> findAll() {
        return apartmentDao.findAll();
    }
}
