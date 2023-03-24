package org.example.rsocket.lec03PeerToPeer.service;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.dto.ResponseDto;
import org.example.rsocket.commons.helper.Utils;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class BatchJobService implements RSocket {
    private RSocket rSocket;

    public BatchJobService(RSocket rSocket) {
        this.rSocket = rSocket;
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        RequestDto requestDto = Utils.toObject(payload, RequestDto.class);
        System.out.println("Received requestDto " + requestDto);

        // fireAndForget will be triggered here
        Mono.just(requestDto)
                .delayElement(Duration.ofSeconds(10))
                .doOnNext(currRequestDto -> System.out.println("Emitting " + currRequestDto))
                //.flatMap(currRequestDto -> calculateCubeValue(currRequestDto))
                .flatMap(this::calculateCubeValue)
                .subscribe();

        return Mono.empty();
    }

    // OK
    private Mono<Void> calculateCubeValue(RequestDto requestDto) {
        int input = requestDto.getInput();
        int output = (int) Math.pow(input, 3);
        ResponseDto responseDto = new ResponseDto(input, output);
        System.out.println("responseDto " + responseDto);
        Payload payload = Utils.toPayload(responseDto);
        return this.rSocket.fireAndForget(payload);
    }
}
