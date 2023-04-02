package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathClientManager;
import com.example.springrsocket.shared.dto.ClientConnectionRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ConnectionHandler {
//    @ConnectMapping
//    public Mono<Void> handleConnection() {
//        System.out.println("Connection setup");
//        return Mono.empty();
//    }

//    // we can also pass an object
//    @ConnectMapping
//    public Mono<Void> handleConnection(ClientConnectionRequestDto clientConnectionRequestDto) {
//        System.out.println("Connection setup");
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientConnectionRequestDto));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return clientConnectionRequestDto.getClientSecret().equals("password") ? Mono.empty() : Mono.error(new RuntimeException("Invalid credentials"));
//    }

//    // we can also pass an object & rSocketRequest from client and dispose requester
//    @ConnectMapping
//    public Mono<Void> handleConnection(ClientConnectionRequestDto clientConnectionRequestDto, RSocketRequester requester) throws JsonProcessingException {
//        System.out.println("Connection setup");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientConnectionRequestDto));
//
//        System.out.println("*************");
//        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requester));
//        return clientConnectionRequestDto.getClientSecret().equals("password") ?
//                Mono.empty() :
//                Mono.fromRunnable(() -> requester.rsocketClient().dispose()); // ClosedChannelException
//    }

    private final MathClientManager mathClientManager;

    @Autowired
    public ConnectionHandler(MathClientManager mathClientManager) {
        this.mathClientManager = mathClientManager;
    }

    // we can use a connection manager
    @ConnectMapping
    public Mono<Void> handleConnection(RSocketRequester requester) {
        System.out.println("Connection setup");
        return Mono.fromRunnable(() -> {
           this.mathClientManager.add(requester);
        });
    }


    @ConnectMapping("math.events.connection")
    public Mono<Void> handleConnectionUpdates(RSocketRequester requester) {
        System.out.println("Events Connection setup");
        return Mono.fromRunnable(() -> {
            this.mathClientManager.add(requester);
        });
    }
}
