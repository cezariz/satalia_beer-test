package com.juliusramonas.beertest.component;

import com.juliusramonas.beertest.service.model.Boundary;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class HaversineTest {

    private final Haversine haversine = new Haversine();

    @Test
    void given_theseCoordinates_whenCalculateDistance_thenThisResult() {
        final SoftAssertions softAssertions = new SoftAssertions();

        final double expected = 2411.587840220508;
        final var actual = haversine.calculateDistance(30.22340011596680, -97.76969909667969, 37.78250122070313,
                -122.39299774169922);

        softAssertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenGoodData_whenGetBoundaries_thenThisResultIsReturned() {
        final SoftAssertions softAssertions = new SoftAssertions();

        final var expected = new Boundary(39.23240912497581, 21.21439110695779, -87.36171892339303,
                -108.17767926996635);

        final var actual = haversine.getBoundaries(30.22340011596680, -97.76969909667969, 1000);
        softAssertions.assertThat(actual)
                .isEqualTo(expected);
    }

}