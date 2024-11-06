package org.andersen.model;

import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ApartmentStatusEnum getStatus() {
        return apartmentStatus;
    }

    public void setStatus(ApartmentStatusEnum status) {
        this.apartmentStatus = status;
    }

    public String getNameOfClient() {
        return nameOfClient;
    }

    public void setNameOfClient(String nameOfClient) {
        this.nameOfClient = nameOfClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && price == apartment.price && apartmentStatus == apartment.apartmentStatus && Objects.equals(nameOfClient, apartment.nameOfClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, apartmentStatus, nameOfClient);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", price=" + price +
                ", status=" + apartmentStatus +
                ", nameOfClient='" + nameOfClient + '\'' +
                '}';
    }
}
