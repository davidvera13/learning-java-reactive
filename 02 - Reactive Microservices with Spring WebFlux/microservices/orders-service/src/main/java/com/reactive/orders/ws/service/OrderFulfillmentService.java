package com.reactive.orders.ws.service;

import com.reactive.orders.ws.client.ProductClient;
import com.reactive.orders.ws.client.TransactionClient;
import com.reactive.orders.ws.client.UserClient;
import com.reactive.orders.ws.dto.RequestContext;
import com.reactive.orders.ws.dto.request.PurchaseOrderRequestDto;
import com.reactive.orders.ws.dto.response.PurchaseOrderResponseDto;
import com.reactive.orders.ws.io.repository.PurchaseOrderRepository;
import com.reactive.orders.ws.mappers.EntityDtoMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {
    private final PurchaseOrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final TransactionClient transactionClient;

    @Autowired
    public OrderFulfillmentService(PurchaseOrderRepository orderRepository,
                                   ProductClient productClient,
                                   UserClient userClient,
                                   TransactionClient transactionClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
        this.transactionClient = transactionClient;
    }

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoMappers::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoMappers::getPurchaseOrder)
                .map(this.orderRepository::save) // blocking
                .map(EntityDtoMappers::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc){
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                // we want to relaunch query 5 times if we didn't get response
                // .retry(5)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc){
        return this.transactionClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto) // setting rc updated
                .thenReturn(rc);
    }


}
