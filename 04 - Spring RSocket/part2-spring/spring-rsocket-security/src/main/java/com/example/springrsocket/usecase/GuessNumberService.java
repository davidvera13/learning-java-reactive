package com.example.springrsocket.usecase;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GuessNumberService {
    public Flux<GuessNumberResponseEnum> play(Flux<Integer> flux) {
        int serverNumber = ThreadLocalRandom.current().nextInt(1, 100);
        System.out.println("number to find: " + serverNumber);
        return flux.map(i ->this.compare(serverNumber, i));

    }

    private GuessNumberResponseEnum compare(int serverNumber, int clientNumber) {
        long signum = Long.signum((long) serverNumber - clientNumber);
        return switch ((int) signum) {
            case -1 -> GuessNumberResponseEnum.LESSER; // serverNumber < clientNumber => 10 - 12
            case 1 -> GuessNumberResponseEnum.GREATER; // serverNumber > clientNumber => 15 - 10
            default -> GuessNumberResponseEnum.EQUAL;  // if not greater or lesser, it's equal ...
        };
    }
}
