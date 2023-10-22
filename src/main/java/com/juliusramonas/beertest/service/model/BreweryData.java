package com.juliusramonas.beertest.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class BreweryData {

    private String id;
    private String name;
    private String country;
    private String phone;
    private String website;
    private Set<BeerData> beers = new HashSet<>();

}
