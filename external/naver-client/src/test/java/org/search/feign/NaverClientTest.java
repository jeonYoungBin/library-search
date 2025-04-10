package org.search.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = NaverClientTest.TestContextConfiguration.class)
@ActiveProfiles("test")
class NaverClientTest {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestContextConfiguration { }

    @Autowired
    NaverClient naverClient;

    @Test
    void callNaver() {
        String http = naverClient.searchBook("HTTP", 1, 10);
        System.out.println(http);

        assertFalse(http.isEmpty());
    }
}
