package com.reactive.orders.ws.controller;

import com.reactive.orders.ws.dto.request.PurchaseOrderRequestDto;
import com.reactive.orders.ws.dto.response.PurchaseOrderResponseDto;
import com.reactive.orders.ws.service.OrderFulfillmentService;
import com.reactive.orders.ws.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class PurchaseOrderController {
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderQueryService queryService;

    @Autowired
    public PurchaseOrderController(OrderFulfillmentService orderFulfillmentService, OrderQueryService queryService) {
        this.orderFulfillmentService = orderFulfillmentService;
        this.queryService = queryService;
    }

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> order(@RequestBody Mono<PurchaseOrderRequestDto> requestDtoMono){
        return this.orderFulfillmentService.processOrder(requestDtoMono)
                .map(ResponseEntity::ok)
                // we check exception, first one may be a bad request sent
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                // second exception is when for a reason other webservice is down
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping("user/{userId}")
    public Flux<PurchaseOrderResponseDto> getOrdersByUserId(@PathVariable long userId){
        return this.queryService.getProductsByUserId(userId);
    }
}
