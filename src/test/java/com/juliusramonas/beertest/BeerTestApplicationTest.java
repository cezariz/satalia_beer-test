package com.juliusramonas.beertest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
public class BeerTestApplicationTest {

    @Test
    public void contextLoads() {
        // if the Spring context loads without any issues, this test will pass
    }
}
