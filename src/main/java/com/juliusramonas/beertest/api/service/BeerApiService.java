package com.juliusramonas.beertest.api.service;

import com.juliusramonas.beertest.api.model.WaypointWebDto;
import com.juliusramonas.beertest.api.model.mapper.WaypointMapper;
import com.juliusramonas.beertest.service.WaypointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerApiService {

    private final WaypointService waypointService;
    private final WaypointMapper waypointMapper;

    public List<WaypointWebDto> getBestRoute(final Double latitude, final Double longitude) {
        final var route = waypointService.calculateWaypoints(latitude, longitude);
        return route.stream()
                .map(waypointMapper::mapDataToDto)
                .toList();
    }

    public List<WaypointWebDto> getBestRouteOptimized(final Double latitude, final Double longitude) {
        final var route = waypointService.calculateWaypointsOptimized(latitude, longitude);
        return route.stream()
                .map(waypointMapper::mapDataToDto)
                .toList();
    }

}
