package com.juliusramonas.beertest.component;

import com.juliusramonas.beertest.service.model.Boundary;
import org.springframework.stereotype.Component;

@Component
public class Haversine {

    private static final int EARTH_RADIUS = 6371;
    private static final double LAT_DISTANCE = 111; // Roughly 111 km per latitude degree.

    public double calculateDistance(double startLat, final double startLong, double endLat, final double endLong) {
        final double dLat = Math.toRadians((endLat - startLat));
        final double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        final double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public Boundary getBoundaries(final double latitude, final double longitude, final double distance) {
        final double dLat = distance / LAT_DISTANCE;
        final double upperLat = latitude + dLat;
        final double lowerLat = latitude - dLat;

        // For longitude, calculate the distance at this particular latitude.
        final double r = EARTH_RADIUS * Math.cos(Math.toRadians(latitude)); // Radius at given latitude.
        final double dLong = distance / (Math.PI * r / 180.0); // Convert radians to degrees.

        final double upperLong = longitude + dLong;
        final double lowerLong = longitude - dLong;

        return new Boundary(upperLat, lowerLat, upperLong, lowerLong);
    }

    private double haversine(final double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

}
