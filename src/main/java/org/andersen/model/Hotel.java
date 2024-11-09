package org.andersen.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hotel {

    private List<Apartment> apartments = new ArrayList<>();

}
