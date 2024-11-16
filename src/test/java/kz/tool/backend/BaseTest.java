package kz.tool.backend;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import kz.tool.backend.container_initializr.GotenbergContainerInitializr;
import kz.tool.backend.container_initializr.MinioContainerInitializr;
import kz.tool.backend.container_initializr.PostgresContainerInitializr;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {
        PostgresContainerInitializr.class,
        MinioContainerInitializr.class,
        GotenbergContainerInitializr.class
})
public abstract class BaseTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    public void initBaseTest() {
        RestAssured.port = port;
    }
}
