package org.search.service

import org.search.controller.response.PageResult
import org.search.controller.response.SearchResponse
import org.search.controller.response.StatResponse
import org.search.entity.DailyStat
import org.search.repository.DailyStatRepository
import org.search.service.event.SearchEvent
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

import java.time.LocalDate

class BookApplicationServiceTest extends Specification {
    BookApplicationService bookApplicationService

    BookQueryService bookQueryService = Mock(BookQueryService)
    DailyStatQueryService dailyStatQueryService = Mock(DailyStatQueryService)
    ApplicationEventPublisher eventPublisher = Mock(ApplicationEventPublisher)


    void setup() {
        bookApplicationService = new BookApplicationService(bookQueryService, dailyStatQueryService, eventPublisher)
    }

    def "search메서드 호출시 검색결과를 반환하면서 통계데이터를 저장한다."() {
        given:
        def givenQuery = "HTTP완벽가이드"
        def givenPage = 1
        def givenSize = 10

        when:
        bookApplicationService.search(givenQuery, givenPage, givenSize)

        then:
        1 * bookQueryService.search(*_) >> {
            String query, Integer page, Integer size ->
                assert query == givenQuery
                assert page == givenPage
                assert size == givenSize

                new PageResult<>(1, 10, 1, [Mock(SearchResponse)])
        }

        and: "저장 이벤트를 발행한다."
        1 * eventPublisher.publishEvent(_ as SearchEvent)
    }

    def "findQueryCount메서드 호출시 인자를 그대로 넘긴다"() {
        given:
        def givenQuery = "HTTP"
        def givenDate = LocalDate.of(2025, 4, 29)

        when:
        bookApplicationService.findQueryCount(givenQuery, givenDate)

        then:
        1 * dailyStatQueryService.findQueryCount(*_) >> {
            String query, LocalDate date ->
                assert query == givenQuery
                assert date == givenDate
        }

    }

    def "findTop5Query메서드 호출시 dailyStatQueryService의 findTop5Query가 호출된다"() {
        when:
        bookApplicationService.findTop5Query()

        then:
        1 * dailyStatQueryService.findTop5Query()
    }
}
