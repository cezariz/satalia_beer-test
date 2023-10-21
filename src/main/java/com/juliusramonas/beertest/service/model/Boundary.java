package com.juliusramonas.beertest.service.model;

public record Boundary(
        double upperLat,
        double lowerLat,
        double upperLong,
        double lowerLong
) {

    @Override
    public String toString() {
        return "Boundary{" + "upperLat=" + upperLat + ", lowerLat=" + lowerLat + ", upperLong=" + upperLong +
                ", lowerLong=" + lowerLong + '}';
    }
}
