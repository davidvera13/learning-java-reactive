package com.example.springrsocket.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class GuessNumberController {
    private final GuessNumberService guessNumberService;

    @Autowired
    public GuessNumberController(GuessNumberService guessNumberService) {
        this.guessNumberService = guessNumberService;
    }

     @MessageMapping("guess")
    public Flux<GuessNumberResponseEnum> play(Flux<Integer> flux) {
        return this.guessNumberService.play(flux);
     }
}
