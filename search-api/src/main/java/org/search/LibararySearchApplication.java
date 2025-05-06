package org.search;

import org.search.feign.KakaoClient;
import org.search.feign.NaverClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {NaverClient.class, KakaoClient.class})
@SpringBootApplication
public class LibararySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibararySearchApplication.class, args);
    }
}
