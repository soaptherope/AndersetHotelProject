package org.andersen.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Hotel implements Serializable {

    private List<Apartment> apartments = new ArrayList<>();

}
