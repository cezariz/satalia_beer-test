package com.juliusramonas.beertest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

@SpringBootTest
@Disabled
public class AbstractIT {

    private final static String DB_USERNAME = "user";
    private final static String DB_PASSWORD = "password";

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine").withDatabaseName("database_test")
                                                           .withUsername(DB_USERNAME)
                                                           .withPassword(DB_PASSWORD)
                                                           .withCopyFileToContainer(
                                                                   MountableFile.forClasspathResource("/beer-data/"),
                                                                   "/tmp/");

    @BeforeAll
    static void init() {
        postgres.start();
    }

    @DynamicPropertySource
    static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/database_test", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> DB_USERNAME);
        registry.add("spring.datasource.password", () -> DB_PASSWORD);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

}
