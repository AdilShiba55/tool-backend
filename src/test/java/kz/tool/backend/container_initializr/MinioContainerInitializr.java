package kz.tool.backend.container_initializr;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class MinioContainerInitializr implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final int EXPOSED_PORT = 9000;

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();
        String login = env.getProperty("app.minio.login");
        String password = env.getProperty("app.minio.password");

        GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("minio/minio:latest"))
                .withEnv("MINIO_ROOT_USER", login)
                .withEnv("MINIO_ROOT_PASSWORD", password)
                .withCommand("server /data")
                .withExposedPorts(EXPOSED_PORT);

        container.start();

        String url = "http://" + container.getHost() + ":" + container.getMappedPort(EXPOSED_PORT);

        TestPropertyValues values = TestPropertyValues.of(
                "app.minio.url=" + url
        );

        values.applyTo(applicationContext);
    }
}
