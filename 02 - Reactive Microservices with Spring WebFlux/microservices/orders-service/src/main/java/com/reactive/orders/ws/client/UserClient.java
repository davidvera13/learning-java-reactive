package com.reactive.orders.ws.client;

import com.reactive.orders.ws.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }



    public Flux<UserDto> getUsers(){
        return this.webClient
                .get()
                 //.uri("")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }


}
