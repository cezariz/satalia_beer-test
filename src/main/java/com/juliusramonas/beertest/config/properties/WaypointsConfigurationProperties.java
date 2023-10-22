package com.juliusramonas.beertest.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "waypoint")
public record WaypointsConfigurationProperties(
        double maxDistance,
        int maxTourSize,
        int closestPointLimit,
        int mostBeersLimit
) {}
