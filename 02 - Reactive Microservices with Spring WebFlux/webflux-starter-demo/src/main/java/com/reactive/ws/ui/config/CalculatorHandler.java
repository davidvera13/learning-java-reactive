package com.reactive.ws.ui.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Component
public class CalculatorHandler {

    // creating multiple handler
    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        // replace the logic with process method: avoid repetitive code
        // int valA = getValue(request, "a");
        // int valB = getValue(request, "b");
        // return ServerResponse.ok().bodyValue(a+b);
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }
    public Mono<ServerResponse> subtractionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a - b));
    }
    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (a, b) -> b != 0 ?
                    ServerResponse.ok().bodyValue(a / b) :
                    ServerResponse.badRequest().bodyValue("b can not be equal to 0")
        );
    }

    private Mono<ServerResponse> process(ServerRequest request, BiFunction<Integer, Integer, Mono<ServerResponse>> operationLogic) {
        int valA = getValue(request, "a");
        int valB = getValue(request, "b");
        return operationLogic.apply(valA ,valB);
    }
    private int getValue(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable(key));
    }
}
