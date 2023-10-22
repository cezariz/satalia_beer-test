package com.juliusramonas.beertest.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Point {

    private Long id;
    private Double lat;
    private Double lon;
    private Double distanceTraveled;
    private Double distanceToPreviousPoint;
    private int beerCount;

}
