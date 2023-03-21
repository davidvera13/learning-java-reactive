package org.example.rsocket.lec01FireAndForget.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.helper.Utils;
import reactor.core.publisher.Mono;

public class MathServiceObjects implements RSocket {
    // implement fire and forget
    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        System.out.println("Receiving object " + Utils.toObject(payload, RequestDto.class));
        return Mono.empty();
    }
}
