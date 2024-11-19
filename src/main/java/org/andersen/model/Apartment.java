package org.andersen.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "apartments")
public class Apartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "apartment_status", nullable = false)
    private ApartmentStatusEnum apartmentStatus;

    @Column(name = "client_name", length = 40)
    private String nameOfClient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Apartment(double price) {
        this.hotel = new Hotel("Andersen");
        this.price = price;
        this.apartmentStatus = ApartmentStatusEnum.FREE;
        this.nameOfClient = "";
    }
}
