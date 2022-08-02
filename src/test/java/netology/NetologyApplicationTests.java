package netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NetologyApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        final var HOST = "http://localhost:";
        final var devPort = 8080;
        final var prodPort = 8081;
        final var urlDev = HOST + devApp.getMappedPort(devPort) + "/profile";
        final var urlProd = HOST + prodApp.getMappedPort(prodPort) + "/profile";
        final var devEntity = restTemplate.getForEntity(urlDev, String.class);
        final var prodEntity = restTemplate.getForEntity(urlProd, String.class);
        Assertions.assertEquals("Current profile is dev", devEntity.getBody());
        Assertions.assertEquals("Current profile is production", prodEntity.getBody());
    }

}
