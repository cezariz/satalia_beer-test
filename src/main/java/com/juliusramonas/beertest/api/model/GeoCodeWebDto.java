package com.juliusramonas.beertest.api.model;

import lombok.Builder;

@Builder
public record GeoCodeWebDto(
        Long id,
        Double latitude,
        Double longitude,
        String accuracy
) {}
