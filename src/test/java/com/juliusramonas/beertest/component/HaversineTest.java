package com.juliusramonas.beertest.component;

import com.juliusramonas.beertest.AbstractIntegrationTest;
import com.juliusramonas.beertest.dao.GeoCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@Slf4j
class HaversineTest extends AbstractIntegrationTest {

    @Autowired
    GeoCodeRepository geoCodeRepository;

    @Autowired
    Haversine haversine;

    @Test
    void given_theseCoordinates_whenCalculateDistance_thenThisResult() {
        final var geoCode1 = geoCodeRepository.findById(1L)
                                              .orElseThrow();
        final var geoCode2 = geoCodeRepository.findById(2L)
                                              .orElseThrow();

        final var distanceInKm =
                haversine.calculateDistance(geoCode1.getLatitude(), geoCode1.getLongitude(), geoCode2.getLatitude(),
                        geoCode2.getLongitude());

        log.info("Distance: %.2fkm".formatted(distanceInKm));

        assertThat(distanceInKm).isCloseTo(2411.587840220508, Percentage.withPercentage(1));
    }

}