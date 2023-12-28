package com.juliusramonas.beertest.extension;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;


@SpringBootTest
@Disabled
public class DataBaseExtension implements BeforeAllCallback, AfterAllCallback {

  private final static String DB_USERNAME = "user";
  private final static String DB_PASSWORD = "password";

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:15-alpine").withDatabaseName("database_test").withUsername(DB_USERNAME)
      .withPassword(DB_PASSWORD)
      .withCopyFileToContainer(MountableFile.forClasspathResource("/beer-data/"), "/tmp/");

  @DynamicPropertySource
  static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url",
        () -> String.format("jdbc:postgresql://localhost:%d/database_test",
            postgres.getFirstMappedPort()));
    registry.add("spring.datasource.username", () -> DB_USERNAME);
    registry.add("spring.datasource.password", () -> DB_PASSWORD);
  }

  @Override
  public void beforeAll(final ExtensionContext extensionContext) {
    postgres.start();
  }

  @Override
  public void afterAll(ExtensionContext extensionContext) throws Exception {
    postgres.stop();
  }

}
