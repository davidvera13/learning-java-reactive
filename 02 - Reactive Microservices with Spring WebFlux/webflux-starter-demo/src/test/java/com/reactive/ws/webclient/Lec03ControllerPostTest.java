package com.reactive.ws.webclient;

import com.reactive.ws.ui.controller.CalculationReactiveController;
import com.reactive.ws.ui.dto.CalculationRequestDto;
import com.reactive.ws.ui.dto.ResponseDto;
import com.reactive.ws.ui.service.CalculationReactiveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CalculationReactiveController.class)
public class Lec03ControllerPostTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private CalculationReactiveService reactiveMathService;

    @Test
    public void postTest(){
        Mockito.when(reactiveMathService.doOperation(Mockito.any())).thenReturn(Mono.just(new ResponseDto(1)));

        this.client
                .post()
                .uri("/reactiveCalc/operation")
                .accept(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBasicAuth("username", "password"))
                .headers(h -> h.set("somekey", "somevalue"))
                .bodyValue(new CalculationRequestDto())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }


}