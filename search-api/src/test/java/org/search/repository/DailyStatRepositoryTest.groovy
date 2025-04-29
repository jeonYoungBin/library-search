package org.search.repository

import jakarta.persistence.EntityManager
import org.search.entity.DailyStat
import org.search.feign.NaverClient
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.time.LocalDateTime

@ActiveProfiles("test")
@DataJpaTest
class DailyStatRepositoryTest extends Specification {
    @Autowired
    DailyStatRepository dailyStatRepository

    @Autowired
    EntityManager entityManager

    @SpringBean
    NaverClient client = Mock()

    def "저장후 조회가된다."() {
        given:
        def givenQuery = "HTTP"

        when:
        def stat = new DailyStat(givenQuery, LocalDateTime.now())
        def saved = dailyStatRepository.saveAndFlush(stat)

        then: "실제 저장이 된다."
        saved.id != null

        when: "클리어 한번하고 재조회 한다."
        entityManager.clear()
        def result = dailyStatRepository.findById(saved.id)

        then:
        verifyAll {
            result.isPresent()
            result.get().query == givenQuery
        }

    }

    def "쿼리의 카운트를 조회한다."() {
        given:
        def givenQuery = "HTTP"
        def now = LocalDateTime.of(2025,4 ,29,0,0,0)
        def stat1 = new DailyStat(givenQuery, now.plusMinutes(10))
        def stat2 = new DailyStat(givenQuery, now.minusMinutes(1))
        def stat3 = new DailyStat(givenQuery, now.plusMinutes(10))
        def stat4 = new DailyStat("JAVA", now.plusMinutes(10))

        def save = dailyStatRepository.saveAll([stat1, stat2, stat3, stat4])
        when:
        def result = dailyStatRepository.countByQueryAndEventDateTimeBetween(givenQuery, now, now.plusDays(1))
        then:
        result == 2

    }
}
