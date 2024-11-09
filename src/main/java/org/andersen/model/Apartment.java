package org.andersen.model;

import lombok.Data;

@Data
public class Apartment {

    private static int apartmentId;

    private int id;

    private double price;

    private ApartmentStatusEnum apartmentStatus;

    private String nameOfClient;

    public Apartment(double price) {
        this.id = ++apartmentId;
        this.price = price;
        this.apartmentStatus = ApartmentStatusEnum.FREE;
        this.nameOfClient = "";
    }
}
