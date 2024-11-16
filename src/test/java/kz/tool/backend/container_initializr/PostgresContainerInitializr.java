package kz.tool.backend.container_initializr;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresContainerInitializr implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final int EXPOSED_PORT = 5432;
    private final String DATABASE_NAME = "test_database";

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("postgres:latest"))
                .withEnv("POSTGRES_DB", DATABASE_NAME)
                .withEnv("POSTGRES_USER", username)
                .withEnv("POSTGRES_PASSWORD", password)
                .withExposedPorts(EXPOSED_PORT);

        container.start();

        String url = "jdbc:postgresql://" +
                container.getHost() + ":" +
                container.getMappedPort(EXPOSED_PORT) + "/" + DATABASE_NAME;

        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url=" + url,
                "spring.datasource.username=" + username,
                "spring.datasource.password=" + password
        );

        values.applyTo(applicationContext);
    }
}
