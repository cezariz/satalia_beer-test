package com.juliusramonas.beertest.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class WaypointData {

    private Double lat;
    private Double lon;
    private BreweryData brewery;
    private Double distanceTraveled;
    private Double distanceToPreviousPoint;
    private Double distanceToStart;

}
