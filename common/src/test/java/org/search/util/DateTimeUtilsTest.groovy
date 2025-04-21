package org.search.util

import spock.lang.Specification

import java.time.LocalDate

class DateTimeUtilsTest extends Specification {
    def "문자열(yyyyMMdd)을 LocalDate 객체로 반환 한다."() {
        given:
        def date = "20240101"

        when:
        def result = DateTimeUtils.parse(date)

        then:
        result == LocalDate.of(2024,1,1)
    }
}
