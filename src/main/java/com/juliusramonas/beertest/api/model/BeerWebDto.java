package com.juliusramonas.beertest.api.model;

import lombok.Builder;

@Builder
public record BeerWebDto(
        Long id,
        String name
) {}
