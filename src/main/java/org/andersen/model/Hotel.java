package org.andersen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Apartment> apartments = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }
}
