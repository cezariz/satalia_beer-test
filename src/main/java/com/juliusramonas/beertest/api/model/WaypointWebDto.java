package com.juliusramonas.beertest.api.model;

import lombok.Builder;

@Builder
public record WaypointWebDto(
        Double lat,
        Double lon,
        BreweryWebDto brewery,
        Double distanceTraveled,
        Double distanceToPreviousPoint,
        Double distanceToStart
) {}
