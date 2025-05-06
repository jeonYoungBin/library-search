package org.search.feign;

import org.search.KakaoBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoClient", url ="${spring.external.kakao.url}", configuration = KakaoClientConfiguration.class)
public interface KakaoClient {
    @GetMapping("/v3/search/book")
    KakaoBookResponse searchBook(@RequestParam("query") String query,
                                 @RequestParam("page") int page,
                                 @RequestParam("size") int size);
}
