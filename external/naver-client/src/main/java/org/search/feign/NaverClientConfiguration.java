package org.search.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class NaverClientConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor(@Value("${spring.external.naver.headers.client-id}")String clientId,
                                                 @Value("${spring.external.naver.headers.client-secret}")String clientSecret) {
        return requestTemplate -> requestTemplate
                                                .header("X-Naver-Client-Id", clientId)
                                                .header("X-Naver-Client-Secret", clientSecret);
    }
}
