package com.juliusramonas.beertest;

import com.juliusramonas.beertest.extension.DataBaseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ExtendWith(DataBaseExtension.class)
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
public class BeerTestApplicationTest {

    @Test
    public void contextLoads() {
        // if the Spring context loads without any issues, this test will pass
    }
}
