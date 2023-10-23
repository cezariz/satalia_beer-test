package com.juliusramonas.beertest.api.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record LocationForm(
        @NotNull(message = "Latitude is required.")
        @Digits(integer = 18, fraction = 14,
                message = "Latitude can have up to 18 digits before the decimal and 14 digits after.")
        Double latitude,

        @NotNull(message = "Longitude is required.")
        @Digits(integer = 18, fraction = 14,
                message = "Longitude can have up to 18 digits before the decimal and 14 digits after.")
        Double longitude,
        boolean optimize
) {}
