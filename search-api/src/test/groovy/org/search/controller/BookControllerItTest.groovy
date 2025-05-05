package org.search.controller

import org.apache.tomcat.util.http.parser.MediaType
import org.search.controller.request.SearchRequest
import org.search.controller.response.PageResult
import org.search.controller.response.SearchResponse
import org.search.service.BookQueryService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerItTest extends Specification {
    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookQueryService bookQueryService = Mock()

    //정상인지, 잘못된 인지(3가지)
    def "정상인자로 요청시 성공한다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page : 1, size : 10)

        and:
        bookQueryService.search(*_) >> new PageResult<>(1, 10, 10, [Mock(SearchResponse)])

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.getQuery())
                .param("page", request.getPage().toString())
                .param("size", request.getSize().toString()))

        then:
        result.andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath('$.totalElements').value(10))
                .andExpect(jsonPath('$.page').value(1))
                .andExpect(jsonPath('$.size').value(10))
                .andExpect(jsonPath('$.contents').isArray())
    }

    def "query가 비어있을때 BadRequest값이 반환된다."() {
        given:
        def request = new SearchRequest(query: "", page : 1, size : 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.getQuery())
                .param("page", request.getPage().toString())
                .param("size", request.getSize().toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errMessage').value("입력은 비어있을 수 없습니다."))
    }

    def "page가 음수일 경우 BadRequest값이 반환된다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page : -1, size : 10)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.getQuery())
                .param("page", request.getPage().toString())
                .param("size", request.getSize().toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errMessage').value("페이지번호는 1이상이어야 합니다."))
    }

    def "size가 음수일 경우 BadRequest값이 반환된다."() {
        given:
        def request = new SearchRequest(query: "HTTP", page : 1, size : -1)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/books")
                .param("query", request.getQuery())
                .param("page", request.getPage().toString())
                .param("size", request.getSize().toString()))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath('$.errMessage').value("사이즈번호는 1이상이어야 합니다."))
    }
}
