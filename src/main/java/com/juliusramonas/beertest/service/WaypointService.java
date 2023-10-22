package com.juliusramonas.beertest.service;

import com.juliusramonas.beertest.component.Haversine;
import com.juliusramonas.beertest.component.advice.TrackExecutionTime;
import com.juliusramonas.beertest.config.properties.WaypointsConfigurationProperties;
import com.juliusramonas.beertest.dao.model.Brewery;
import com.juliusramonas.beertest.service.mapper.WaypointDataMapper;
import com.juliusramonas.beertest.service.model.Point;
import com.juliusramonas.beertest.service.model.WaypointData;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class WaypointService {

    private final BreweryService breweryService;
    private final Haversine haversine;
    private final WaypointsConfigurationProperties properties;
    private final WaypointDataMapper waypointDataMapper;

    @TrackExecutionTime
    public List<WaypointData> calculateWaypoints(final Double latitude, final Double longitude) {
        final var boundary = haversine.getBoundaries(latitude, longitude, properties.maxDistance() / 2);
        final var breweries = breweryService.getBreweriesInBoundary(boundary);

        if (breweries.isEmpty()) {
            log.info("No breweries found in the boundary for coordinates:[%f,%f]".formatted(latitude,
                    longitude));
            return Collections.emptyList();
        }

        final Point startPoint = new Point(-1L, latitude, longitude, 0.0, 0.0, 0);

        final Map<Long, Brewery> breweryMap = new HashMap<>();
        final List<Point> allPoints = new ArrayList<>();

        breweries.forEach(brewery -> {
            breweryMap.put(brewery.getId(), brewery);
            allPoints.add(new Point(brewery.getId(), brewery.getGeoCode().getLatitude(),
                    brewery.getGeoCode().getLongitude(), 0.0, 0.0, brewery.getBeers().size()));
        });

        final List<List<Point>> allTours = new ArrayList<>();
        exploreTours(startPoint, allPoints, allTours, new ArrayList<>(), 0);

        return allTours.stream()
                .max(Comparator.comparingInt(tour -> tour.stream().mapToInt(Point::getBeerCount).sum()))
                .map(tour -> mapToWaypointData(tour, breweryMap))
                .orElse(Stream.empty())
                .toList();
    }


    private void exploreTours(final Point current, final List<Point> remainingPoints,
            final List<List<Point>> allTours, final List<Point> currentTour,
            final double currentDistance) {
        if (allTours.size() > properties.maxTourSize()) {
            return;
        }

        final List<Point> closestPoints = remainingPoints.stream()
                .sorted(getDistanceComparator(current))
                .limit(properties.closestPointLimit())
                .sorted((o1, o2) -> Integer.compare(o2.getBeerCount(), o1.getBeerCount()))
                .limit(properties.mostBeersLimit())
                .filter(point -> doesNotExceedMaxDistance(current, currentTour, currentDistance, point))
                .toList();

        if (closestPoints.isEmpty()) {
            final double distanceToStart = getDistance(current, currentTour.get(0));
            if (!currentTour.isEmpty() && currentDistance + distanceToStart <= properties.maxDistance()) {
                currentTour.add(currentTour.get(0));
                currentTour.get(currentTour.size() - 1).setDistanceTraveled(currentDistance + distanceToStart);
                currentTour.get(currentTour.size() - 1).setDistanceToPreviousPoint(distanceToStart);
                allTours.add(new ArrayList<>(currentTour));
            }
            return;
        }

        for (final Point point : closestPoints) {
            final double distanceBetweenPoints = getDistance(current, point);
            final double newDistance = currentDistance + distanceBetweenPoints;
            final List<Point> newRemainingPoints = new ArrayList<>(remainingPoints);
            newRemainingPoints.remove(point);
            final List<Point> newCurrentTour = new ArrayList<>(currentTour);
            newCurrentTour.add(current);
            point.setDistanceTraveled(newDistance);
            point.setDistanceToPreviousPoint(distanceBetweenPoints);
            exploreTours(point, newRemainingPoints, allTours, newCurrentTour, newDistance);
        }
    }

    private boolean doesNotExceedMaxDistance(final Point current, final List<Point> currentTour,
            final double currentDistance, final Point point) {
        final var distanceNewPointAndStart = getDistance(point,
                currentTour.isEmpty() ? current : currentTour.get(0));
        final var distanceBetweenPoints = getDistance(current, point);
        return currentDistance + distanceNewPointAndStart + distanceBetweenPoints
                < properties.maxDistance();
    }

    private Double getDistance(final Point point1, final Point point2) {
        return haversine.calculateDistance(point1.getLat(), point1.getLon(), point2.getLat(),
                point2.getLon());
    }

    private Comparator<Point> getDistanceComparator(final Point point) {
        return Comparator.comparingDouble(p -> getDistance(p, point));
    }

    private Stream<WaypointData> mapToWaypointData(final List<Point> tour,
            final Map<Long, Brewery> breweryMap) {
        return tour.stream().map(
                point -> waypointDataMapper.breweryAndPointToWaypointData(breweryMap.get(point.getId()),
                        point));
    }

}
