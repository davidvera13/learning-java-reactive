package com.example.springrsocket.controller;

import com.example.springrsocket.shared.dto.ClientConnectionRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    // we can also pass an object
    @ConnectMapping
    public Mono<Void> handleConnection(ClientConnectionRequestDto clientConnectionRequestDto) {
        System.out.println("Connection setup");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(clientConnectionRequestDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return clientConnectionRequestDto.getClientSecret().equals("password") ? Mono.empty() : Mono.error(new RuntimeException("Invalid credentials"));
    }
}
