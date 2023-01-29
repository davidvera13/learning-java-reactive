package com.reactive.ws.utils;

import ch.qos.logback.core.net.server.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080") // usually should be service name or load balanced url
                // .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                // .filter((clientRequest, exchangeFunction) -> sessionToken(clientRequest, exchangeFunction))
                .filter(this::sessionToken)
                .build();
    }


    //private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
    //    System.out.println("Generating session token");
    //    ClientRequest clientRequest = ClientRequest.from(request)
    //            .headers(h -> h.setBearerAuth("some.bearer.jwt"))
    //            .build();
    //
    //    return ex.exchange(clientRequest);
    //}

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        System.out.println("Generating session token");
        // auth -> basic or jwt
        ClientRequest clientRequest = request.attribute("auth") // check for key auth
                .map(v -> v.equals("basic") ? withBasicAuth(request): withJwt(request)) // if header contains basic, use basic auth
                .orElse(request);
        // replacing the request with clientRequest
        return ex.exchange(clientRequest);
    }



    private ClientRequest withBasicAuth(ClientRequest request) {
        // request is immutable: we use a copy
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("userName", "password"))
                .build();
    }

    private ClientRequest withJwt(ClientRequest request) {
        // request is immutable: we use a copy
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("some.jwt.validtoken"))
                .build();
    }
}
