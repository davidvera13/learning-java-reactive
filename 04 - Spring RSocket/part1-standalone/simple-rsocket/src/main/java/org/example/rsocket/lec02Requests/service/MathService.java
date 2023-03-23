package org.example.rsocket.lec02Requests.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.example.rsocket.commons.dto.ChartResponseDto;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.dto.ResponseDto;
import org.example.rsocket.commons.helper.Utils;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    // returns a flux of payloads
    @Override
    public Flux<Payload> requestStream(Payload payload) {
        RequestDto requestDto = Utils.toObject(payload, RequestDto.class);
        return Flux.range(1, 10)
                .map(integer -> integer * requestDto.getInput())
                .map(integer -> new ResponseDto(requestDto.getInput(), integer))
                .delayElements(Duration.ofSeconds(1)) // let's assume it's a time consuming process
                .doOnNext(System.out::println)

                .doFinally(s -> System.out.println(s))
                .map(Utils::toPayload);
        // return RSocket.super.requestStream(payload);
    }


    @Override
    public Flux<Payload> requestChannel(Publisher<Payload> payloads) {
        return Flux.from(payloads)
                .map(payload -> Utils.toObject(payload, RequestDto.class))
                .map(RequestDto::getInput)
                .map(i -> new ChartResponseDto(i, (i * i) +1))
                .map(Utils::toPayload);
    }
}
