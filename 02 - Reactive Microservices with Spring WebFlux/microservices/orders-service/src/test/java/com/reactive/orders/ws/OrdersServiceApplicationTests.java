package com.reactive.orders.ws;

import com.reactive.orders.ws.client.ProductClient;
import com.reactive.orders.ws.client.UserClient;
import com.reactive.orders.ws.dto.ProductDto;
import com.reactive.orders.ws.dto.UserDto;
import com.reactive.orders.ws.dto.request.PurchaseOrderRequestDto;
import com.reactive.orders.ws.dto.response.PurchaseOrderResponseDto;
import com.reactive.orders.ws.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrdersServiceApplicationTests {
    private final UserClient userClient;

    private final ProductClient productClient;

    private final OrderFulfillmentService fulfillmentService;

    @Autowired
    public OrdersServiceApplicationTests(UserClient userClient, ProductClient productClient, OrderFulfillmentService fulfillmentService) {
        this.userClient = userClient;
        this.productClient = productClient;
        this.fulfillmentService = fulfillmentService;
    }

    @Test
    void contextLoads() {

        Flux<PurchaseOrderResponseDto> dtoFlux = Flux.zip(userClient.getUsers(), productClient.getProducts())
                .map(t -> buildDto(t.getT1(), t.getT2()))
                .flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);

        StepVerifier.create(dtoFlux)
                .expectNextCount(12)
                .verifyComplete();


    }

    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto){
        PurchaseOrderRequestDto dto = new PurchaseOrderRequestDto();
        dto.setUserId(userDto.getId());
        dto.setProductId(productDto.getId());
        return dto;
    }

}
