package org.search.service

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.search.controller.response.PageResult
import org.search.controller.response.SearchResponse
import org.search.repository.KakaoBookRepository
import org.search.repository.NaverBookRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class BookQueryServiceItTest extends Specification {
    @Autowired
    BookQueryService bookQueryService

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry

    @SpringBean
    KakaoBookRepository kakaoBookRepository = Mock()

    @SpringBean
    NaverBookRepository naverBookRepository = Mock()

    def "정상상황에서는 Circuit의 상태가 CLOSED이고 naver쪽으로 호출이 들어간다." () {
        given:
        def keyword = 'HTTP'
        def page = 1
        def size = 10

        when:
        bookQueryService.search(keyword, page, size)

        then:
        1 * naverBookRepository.search(keyword, page, size) >> new PageResult<>(1, 10, 0, [])

        and:
        def circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers().stream().findFirst().get()
        circuitBreaker.state == CircuitBreaker.State.CLOSED

        and:
        0 * kakaoBookRepository.search(*_)
    }

    def "circuit-breaker가 open되어 kakao쪽으로 요청을 한다.." () {
        given:
        def keyword = 'HTTP'
        def page = 1
        def size = 10
        def config = CircuitBreakerConfig.custom()
                .slidingWindowSize(1)
                .minimumNumberOfCalls(1)
                .failureRateThreshold(50)
                .build()
        def kakaoResponse = new PageResult<>(1, 10, 1, [])

        circuitBreakerRegistry.circuitBreaker("naverSearch", config)

        and: "naver쪽은 항상 예외가 발생한다."
        naverBookRepository.search(keyword, page, size) >> {throw new RuntimeException("error!!")}

        when:
        def result = bookQueryService.search(keyword, page, size)

        then: "kakao쪽으로 Fallback된다."
        1 * kakaoBookRepository.search(keyword, page, size) >> kakaoResponse

        and: "circuit이 open된다."
        def circuitBreaker = circuitBreakerRegistry.getAllCircuitBreakers().stream().findFirst().get()
        circuitBreaker.state == CircuitBreaker.State.OPEN

        and:
        result == kakaoResponse
    }
}
