package com.reactive.ws.webclient;

import com.reactive.ws.ui.config.RequestHandler;
import com.reactive.ws.ui.config.RouterConfig;
import com.reactive.ws.ui.dto.ResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {

    private WebTestClient client;

    @Autowired
    private ApplicationContext ctx;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    public void setClient(){
        this.client = WebTestClient.bindToApplicationContext(ctx).build();

    }

    @Test
    public void test(){

        Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new ResponseDto(225)));

        this.client
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(ResponseDto.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));


    }

}