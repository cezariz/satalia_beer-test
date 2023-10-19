package com.juliusramonas.beertest;

import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

@SpringBootTest
public class AbstractIntegrationTest {

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

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        postgres.start();

        // instructing default connection to use app_user from init.sql
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/database_test", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> DB_USERNAME);
        registry.add("spring.datasource.password", () -> DB_PASSWORD);
        // instructing flyway to use admin user
        registry.add("spring.flyway.url",
                () -> String.format("jdbc:postgresql://localhost:%d/database_test", postgres.getFirstMappedPort()));
        registry.add("spring.flyway.user", () -> DB_USERNAME);
        registry.add("spring.flyway.password", () -> DB_PASSWORD);
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

//    @Test
//    void test() {
//        final var t = em.createNativeQuery("select count(*) from beers")
//                        .getSingleResult();
//    }

}
