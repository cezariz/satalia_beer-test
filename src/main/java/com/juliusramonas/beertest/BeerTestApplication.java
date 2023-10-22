package com.juliusramonas.beertest;

import com.juliusramonas.beertest.config.properties.WaypointsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {WaypointsConfigurationProperties.class})
public class BeerTestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BeerTestApplication.class, args);
    }

}
