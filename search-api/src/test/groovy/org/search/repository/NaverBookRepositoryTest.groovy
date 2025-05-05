package org.search.repository

import org.search.Item
import org.search.NaverBookResponse
import org.search.controller.response.PageResult
import org.search.controller.response.SearchResponse
import org.search.feign.NaverClient
import spock.lang.Specification

class NaverBookRepositoryTest extends Specification {
    BookRepository bookRepository

    NaverClient naverClient = Mock()

    void setup() {
        bookRepository = new NaverBookRepository(naverClient)
    }

    def "search호출시 적절한 데이터형식으로 변환한다."() {
        given:
        def items = [
                new Item(title: "제목", author: "저자", publisher: "출판사", pubDate: "20250101", isbn: "isbn"),
                new Item(title: "제목2", author: "저자2", publisher: "출판사2", pubDate: "20250101", isbn: "isbn")
        ]
        def givenQuery = "HTTP"
        def givenPage = 1
        def givenSize = 2
        def response = new NaverBookResponse(
                lastBuildDate: "Mon, 21 Apr 2025 19:49:41 +0900",
                total: 34,
                start: 1,
                display: 2,
                items: items)
        and:
        1 * naverClient.searchBook("HTTP", 1,2) >> response

        when:
        def result = bookRepository.search(givenQuery, givenPage, givenSize)

        then:
        verifyAll {
            result.size() == 2
            result.page() == 1
            result.totalElements() == 34
            result.contents().size() == 2
        }

    }
}
