package org.andersen.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Hotel implements Serializable {

    private List<Apartment> apartments = new ArrayList<>();

}
