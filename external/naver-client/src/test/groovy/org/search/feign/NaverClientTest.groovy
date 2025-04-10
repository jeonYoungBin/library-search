package org.search.feign

import org.search.NaverBookResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = NaverClientTest.TestConfig.class)
@ActiveProfiles("test")
class NaverClientTest extends Specification {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestConfig {}

    @Autowired
    NaverClient naverClient

    def "naver 호출"() {
        given:
        when:
        def response = naverClient.searchBook("HTTP", 1, 10)

        then:
        print response.total
    }
}
