package org.search.service.event

import org.search.entity.DailyStat
import org.search.service.DailyStatCommandService
import spock.lang.Specification

import java.time.LocalDateTime

class SearchEventHandlerTest extends Specification {

    def "handlerEvent" () {
        given:
        def commendService = Mock(DailyStatCommandService)
        def handler = new SearchEventHandler(commendService)
        def event = new SearchEvent("HTTP", LocalDateTime.now())

        when:
        handler.eventHandler(event)

        then:
        1 * commendService.save(_ as DailyStat)
    }

}
