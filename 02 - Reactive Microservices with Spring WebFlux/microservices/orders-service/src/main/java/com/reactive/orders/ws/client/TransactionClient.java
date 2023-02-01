package com.reactive.orders.ws.client;

import com.reactive.orders.ws.dto.UserDto;
import com.reactive.orders.ws.dto.request.TransactionRequestDto;
import com.reactive.orders.ws.dto.response.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionClient {

    private final WebClient webClient;

    public TransactionClient(@Value("${user.service.transactions.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }
    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto requestDto){
        return this.webClient
                .post()
                // .uri(requestDto.getUserId() + "/transactions")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }


}
