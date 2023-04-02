package com.example.springrsocket.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Controller
@MessageMapping("batch")
public class BatchJobController {

    // fire and forget
    @MessageMapping("job.request")
    public Mono<Void> submitJob(Mono<Integer> integerMono, RSocketRequester rSocketRequester) {
        this.process(integerMono, rSocketRequester);
        return Mono.empty();
    }

    private void process(Mono<Integer> integerMono, RSocketRequester rSocketRequester) {
        integerMono
                .delayElement(Duration.ofSeconds(10))
                .map(val -> (int) Math.pow(val, 3))
                .flatMap(i -> rSocketRequester.route("batch.job.response").data(i).send())
                .subscribe();
    }
}
