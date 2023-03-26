package com.example.springrsocket.client;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ClientController {
    @MessageMapping("batch.job.response")
    public Mono<Void> response(Mono<Integer> integerMono) {
        return integerMono
                .doOnNext(i -> System.out.println("Client received " + i))
                .then();
    }
}
