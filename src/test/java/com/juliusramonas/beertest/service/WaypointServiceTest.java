package com.juliusramonas.beertest.service;

import static org.mockito.Mockito.when;

import com.juliusramonas.beertest.component.Haversine;
import com.juliusramonas.beertest.config.properties.WaypointsConfigurationProperties;
import com.juliusramonas.beertest.dao.model.Beer;
import com.juliusramonas.beertest.dao.model.Brewery;
import com.juliusramonas.beertest.dao.model.GeoCode;
import com.juliusramonas.beertest.service.mapper.BeerDataMapperImpl;
import com.juliusramonas.beertest.service.mapper.BreweryDataMapperImpl;
import com.juliusramonas.beertest.service.mapper.WaypointDataMapper;
import com.juliusramonas.beertest.service.mapper.WaypointDataMapperImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(WaypointsConfigurationProperties.class)
@ContextConfiguration(classes = {Haversine.class, WaypointDataMapperImpl.class, BreweryDataMapperImpl.class,
        BeerDataMapperImpl.class})
@TestPropertySource(properties = {"waypoint.maxDistance=2000", "waypoint.maxTourSize=15000",
        "waypoint.closestPointLimit=2", "waypoint.mostBeersLimit=2"})
@TestInstance(Lifecycle.PER_CLASS)
class WaypointServiceTest {

    WaypointService waypointService;

    @MockBean
    BreweryService breweryService;

    @Autowired
    Haversine haversine;

    @Autowired
    WaypointsConfigurationProperties properties;

    @Autowired
    WaypointDataMapper waypointDataMapper;

    @BeforeAll
    void initMocks() {
        MockitoAnnotations.openMocks(this);
        waypointService = new WaypointService(breweryService, haversine, properties, waypointDataMapper);
    }

    @Test
    void givenNoBreweriesFound_whenCalculateWaypoints_thenEmptyListReturned() {
        when(breweryService.getBreweriesInBoundary(Mockito.any()))
                .thenReturn(Collections.emptyList());

        final var actual = waypointService.calculateWaypoints(51.355468, 11.10079);

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actual).isEmpty();
    }

    @Test
    void givenAllInRangeData_whenCalculateWaypoints_thenThisResult() {
        final var breweries = getInRangeBreweries();
        when(breweryService.getBreweriesInBoundary(Mockito.any()))
                .thenReturn(breweries);
        final var actual = waypointService.calculateWaypoints(51.355468, 11.10079);
        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actual).hasSize(4);
    }

    @Test
    void givenMixedData_whenCalculateWaypoints_thenThisResult() {
        final List<Brewery> breweries = new ArrayList<>(getInRangeBreweries());
        breweries.addAll(getNotInRangeBreweries());
        when(breweryService.getBreweriesInBoundary(Mockito.any()))
                .thenReturn(breweries);
        final var actual = waypointService.calculateWaypoints(51.355468, 11.10079);
        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actual).hasSize(3);
    }

    @Test
    void givenAllInRangeData_whenCalculateWaypointsOptimized_thenThisResult() {
        final var breweries = getInRangeBreweries();
        when(breweryService.getBreweriesInBoundary(Mockito.any()))
                .thenReturn(breweries);
        final var actual = waypointService.calculateWaypointsOptimized(51.355468, 11.10079);
        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actual).hasSize(4);
    }

    @Test
    void givenMixedData_whenCalculateWaypointsOptimized_thenThisResult() {
        final List<Brewery> breweries = new ArrayList<>(getInRangeBreweries());
        breweries.addAll(getNotInRangeBreweries());
        when(breweryService.getBreweriesInBoundary(Mockito.any()))
                .thenReturn(breweries);
        final var actual = waypointService.calculateWaypointsOptimized(51.355468, 11.10079);
        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(actual).hasSize(3);
    }

    private List<Brewery> getInRangeBreweries() {
        final Brewery brewery1 = new Brewery();
        brewery1.setId(1L);
        brewery1.setBeers(createMockBeerSet(1L, 2L));
        brewery1.setGeoCode(createGeocode(1L, 49.13245913492371, 7.17757926996635));

        final Brewery brewery2 = new Brewery();
        brewery2.setId(2L);
        brewery2.setBeers(createMockBeerSet(3L, 4L, 5L));
        brewery2.setGeoCode(createGeocode(2L, 44.13245913492371, 4.21439110695779));

        return List.of(brewery1, brewery2);
    }

    private List<Brewery> getNotInRangeBreweries() {
        final Brewery brewery1 = new Brewery();
        brewery1.setId(3L);
        brewery1.setBeers(createMockBeerSet(6L));
        brewery1.setGeoCode(createGeocode(3L, 44.13245913492371, 3.21439110695779));

        return List.of(brewery1);
    }

    private Set<Beer> createMockBeerSet(final Long... ids) {
        return Stream.of(ids).map(id -> {
            final var beer = new Beer();
            beer.setId(id);
            return beer;
        }).collect(Collectors.toSet());
    }

    private GeoCode createGeocode(final Long id, final Double lat, final Double lon) {
        final var geoCode = new GeoCode();
        geoCode.setId(id);
        geoCode.setLatitude(lat);
        geoCode.setLongitude(lon);

        return geoCode;
    }

}