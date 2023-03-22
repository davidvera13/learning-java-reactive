package org.example.rsocket.lec02Requests.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.dto.ResponseDto;
import org.example.rsocket.commons.helper.Utils;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {
    // implement request and response
    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        return Mono.fromSupplier(() -> {
            RequestDto requestDto = Utils.toObject(payload, RequestDto.class);
            System.out.println("Request received " + requestDto.toString());
            ResponseDto responseDto = new ResponseDto(requestDto.getInput(), (int) Math.pow(requestDto.getInput(), 2));
            return Utils.toPayload(responseDto);
        });
    }
}
