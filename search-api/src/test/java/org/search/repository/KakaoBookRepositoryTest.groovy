package org.search.repository

import org.search.Document
import org.search.Item
import org.search.KakaoBookResponse
import org.search.Meta
import org.search.NaverBookResponse
import org.search.feign.KakaoClient
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class KakaoBookRepositoryTest extends Specification {
    BookRepository bookRepository

    KakaoClient kakaoClient = Mock()

    void setup() {
        bookRepository = new KakaoBookRepository(kakaoClient)
    }

    def "search호출시 적절한 데이터형식으로 변환한다."() {
        given:
        def documents = [
                new Document("제목", ["저자"],  "isbn",  "출판사", "2015-01-26T00:00:00.000+09:00"),
                new Document("제목2", ["저자2"],  "isbn2",  "출판사2", "2015-01-26T00:00:00.000+09:00"),
        ]

        def meta  = new Meta(false, 1, 10)

        def givenQuery = "HTTP"
        def givenPage = 1
        def givenSize = 2
        def response = new KakaoBookResponse(documents, meta)
        and:
        1 * kakaoClient.searchBook("HTTP", 1,2) >> response

        when:
        def result = bookRepository.search(givenQuery, givenPage, givenSize)

        then:
        verifyAll {
            result.size() == 2
            result.page() == 1
            result.totalElements() == 10
            result.contents().size() == 2
        }

    }
}
