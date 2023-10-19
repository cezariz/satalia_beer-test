package com.juliusramonas.beertest;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class BeerTestApplicationTests extends AbstractIntegrationTest {

    @Autowired
    EntityManager em;

    @Test
    void initialDataLoaded() {
        SoftAssertions softAssertions = new SoftAssertions();
        assertTableHasEntries(softAssertions, "beers");
        assertTableHasEntries(softAssertions, "breweries");
        assertTableHasEntries(softAssertions, "categories");
        assertTableHasEntries(softAssertions, "geocodes");
        assertTableHasEntries(softAssertions, "styles");
    }

    private void assertTableHasEntries(final SoftAssertions softAssertions, final String tableName) {
        final String query = "select count(*) from %s".formatted(tableName);
        final var totalEntries = em.createNativeQuery(query)
                                   .getSingleResult();

        log.info("%s has %s entries".formatted(tableName, totalEntries));

        softAssertions.assertThat(Integer.valueOf(totalEntries.toString()))
                      .isGreaterThan(0);
    }

}
