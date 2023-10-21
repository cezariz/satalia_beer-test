package com.juliusramonas.beertest.service;

import com.juliusramonas.beertest.component.Haversine;
import com.juliusramonas.beertest.component.advice.TrackExecutionTime;
import com.juliusramonas.beertest.config.properties.WaypointsConfigurationProperties;
import com.juliusramonas.beertest.dao.model.Brewery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WaypointService {

    private final BreweryService breweryService;
    private final Haversine haversine;
    private final WaypointsConfigurationProperties properties;

//    @PostConstruct
//    public void test() {
//        final var calculatedWaypoints = calculateWaypoints(51.355468, 11.100790);
//
//        log.info("Tour size: " + calculatedWaypoints.size());
//    }

    @TrackExecutionTime
    public List<Brewery> calculateWaypoints(final Double latitude, final Double longitude) {
        final var boundary = haversine.getBoundaries(latitude, longitude, properties.maxDistance() / 2);
        final var breweries = breweryService.getBreweriesInBoundary(boundary);

        if (breweries.isEmpty()) {
            log.info("No breweries found in the boundary for coordinates:[%f,%f]".formatted(latitude, longitude));
            return Collections.emptyList();
        }

        final Point startPoint = new Point(-1L, latitude, longitude);

        final Map<Long, Brewery> breweryMap = new HashMap<>();
        final List<Point> allPoints = new ArrayList<>();

        breweries.forEach(brewery -> {
            breweryMap.put(brewery.getId(), brewery);
            allPoints.add(new Point(brewery.getId(), brewery.getGeoCode()
                                                            .getLatitude(), brewery.getGeoCode()
                                                                                   .getLongitude()));
        });

        final List<List<Point>> allTours = new ArrayList<>();
        exploreTours(startPoint, allPoints, allTours, new ArrayList<>(), 0);

        return allTours.stream()
                       .filter(tour -> calculateTourDistance(tour) <= properties.maxDistance())
                       .max(Comparator.comparingInt(List::size))
                       .stream()
                       .flatMap(points -> mapToBrewery(points, breweryMap))
                       .collect(Collectors.toList());
    }

    private Stream<Brewery> mapToBrewery(final List<Point> tour, final Map<Long, Brewery> breweryMap) {
        return tour.stream()
                   .map(point -> breweryMap.get(point.getId()));
    }

    private void exploreTours(final Point current, final List<Point> remainingPoints, final List<List<Point>> allTours,
                              final List<Point> currentTour, final double currentDistance) {
        if (allTours.size() > properties.maxTourSize()) {
            return;
        }

        final List<Point> closestPoints = remainingPoints.stream()
                                                         .sorted(getDistanceComparator(current))
                                                         .limit(properties.closestPointLimit())
                                                         .filter(point -> currentDistance + getDistance(current,
                                                                 currentTour.isEmpty() ? current : currentTour.get(0)) +
                                                                 getDistance(current, point) <
                                                                 properties.maxDistance())
                                                         .toList();

        if (closestPoints.isEmpty()) {
            final double distanceToStart = getDistance(current, currentTour.get(0));
            if (!currentTour.isEmpty() && currentDistance + distanceToStart <= properties.maxDistance()) {
                currentTour.add(currentTour.get(0));
                allTours.add(new ArrayList<>(currentTour));
            }
            return;
        }

        for (final Point point : closestPoints) {
            final double distanceToNewStart = getDistance(point, currentTour.isEmpty() ? current : currentTour.get(0));
            final double newDistance = currentDistance + getDistance(current, point);
            if (newDistance + distanceToNewStart <= properties.maxDistance()) {
                final List<Point> newRemainingPoints = new ArrayList<>(remainingPoints);
                newRemainingPoints.remove(point);
                final List<Point> newCurrentTour = new ArrayList<>(currentTour);
                newCurrentTour.add(current);
                exploreTours(point, newRemainingPoints, allTours, newCurrentTour, newDistance);
            } else if (!currentTour.isEmpty() && !currentTour.get(0)
                                                             .getId()
                                                             .equals(-1L)) {
                currentTour.add(currentTour.get(0));
                allTours.add(new ArrayList<>(currentTour));
            }
        }
    }

    private double calculateTourDistance(final List<Point> tour) {
        double distance = 0.0;
        for (int i = 1; i < tour.size(); i++) {
            distance += getDistance(tour.get(i - 1), tour.get(i));
        }

        if (!tour.isEmpty()) {
            distance += getDistance(tour.get(tour.size() - 1), tour.get(0));
        }
        return distance;
    }

    private Double getDistance(final Point point1, final Point point2) {
        return haversine.calculateDistance(point1.getLat(), point1.getLon(), point2.getLat(), point2.getLon());
    }

    private Comparator<Point> getDistanceComparator(final Point point) {
        return Comparator.comparingDouble(p -> getDistance(p, point));
    }

    @AllArgsConstructor
    @Setter
    @Getter
    private static class Point {
        private Long id;
        private Double lat;
        private Double lon;
    }
}
