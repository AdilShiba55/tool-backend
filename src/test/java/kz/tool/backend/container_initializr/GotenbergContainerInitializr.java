package kz.tool.backend.container_initializr;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class GotenbergContainerInitializr implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final int EXPOSED_PORT = 3000;

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("thecodingmachine/gotenberg:7"))
                .withExposedPorts(EXPOSED_PORT);

        container.start();

        String url = "http://" + container.getHost() + ":" + container.getMappedPort(EXPOSED_PORT);

        TestPropertyValues values = TestPropertyValues.of(
                "app.gotenberg.url=" + url
        );

        values.applyTo(applicationContext);
    }
}
