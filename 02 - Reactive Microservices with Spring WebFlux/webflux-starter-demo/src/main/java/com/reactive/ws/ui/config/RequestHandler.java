package com.reactive.ws.ui.config;

import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ErrorResponse;
import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.exceptions.InputValidationException;
import com.reactive.ws.ui.service.CalculationReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {
    private final CalculationReactiveService service;

    @Autowired
    public RequestHandler(CalculationReactiveService service) {
        this.service = service;
    }

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        // ServerRequest contains all information of the request, including headers
        // reading integer value in the path
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<ResponseDto> responseDtoMono = this.service.findSquare(input);
        return ServerResponse.ok().body(responseDtoMono, ResponseDto.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<ResponseDto> responseDtoFlux = this.service.multiplicationTable(input);
        return ServerResponse.ok().body(responseDtoFlux, ResponseDto.class);
    }

    public Mono<ServerResponse> tableStreamingHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<ResponseDto> responseDtoFlux = this.service.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseDtoFlux, ResponseDto.class);
    }

    public Mono<ServerResponse> doOperationHandler(ServerRequest serverRequest) {
        // convert the request body to a mono
        Mono<CalculationRequestDto> mono = serverRequest.bodyToMono(CalculationRequestDto.class);
        Mono<ResponseDto> responseDtoMono =  this.service.doOperation(mono);
        return ServerResponse.ok()
                .body(responseDtoMono, ResponseDto.class);
    }


    public Mono<ServerResponse> squareWithValidationHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        if(input < 10|| input > 20 ) {
            return Mono.error(new InputValidationException(input));
        }

        Mono<ResponseDto> responseDtoMono = this.service.findSquare(input);
        return ServerResponse.ok().body(responseDtoMono, ResponseDto.class);
    }

}
