package com.example.springrsocket.controller;

import com.example.springrsocket.service.MathService;
import com.example.springrsocket.shared.dto.ChartResponseDto;
import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("math.service.secured")
public class SecuredMathController {
    private final MathService mathService;

    @Autowired
    public SecuredMathController(MathService mathService) {
        this.mathService = mathService;
    }


    @MessageMapping("square")
    public Mono<ComputationResponseDto> calculateSquare(Mono<ComputationRequestDto> requestDtoMono,
                                                        @AuthenticationPrincipal Mono<UserDetails> userDetailsMono) {
        userDetailsMono.doOnNext(System.out::println).subscribe();
        return mathService.calculateSquare(requestDtoMono);
    }

    // request stream
    // .route("math.service.secured.table").hasRole("ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    @MessageMapping("table")
    public Flux<ComputationResponseDto> tableStream(Mono<ComputationRequestDto> requestDtoMono) {
        // Mono must be converted to flux
        return requestDtoMono
                .flatMapMany(this.mathService::tableStream);
    }
}
