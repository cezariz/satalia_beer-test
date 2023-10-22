package com.juliusramonas.beertest.api.model;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.Set;

@Builder
public record BreweryWebDto(
        Long id,
        String name,
        String address1,
        String address2,
        String city,
        String state,
        String code,
        String country,
        String phone,
        String website,
        String filepath,
        String description,
        Long userId,
        ZonedDateTime lastModified,
        GeoCodeWebDto geoCode,
        Set<BeerWebDto> beers
) {}
