package com.reactive.orders.ws.service;

import com.reactive.orders.ws.dto.response.PurchaseOrderResponseDto;
import com.reactive.orders.ws.io.entity.PurchaseOrder;
import com.reactive.orders.ws.io.repository.PurchaseOrderRepository;
import com.reactive.orders.ws.mappers.EntityDtoMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderQueryService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public OrderQueryService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(long userId){
        // List<PurchaseOrder> purchaseOrderResponseDtoList = this.purchaseOrderRepository.findByUserId(userId); // ?? bad idea doing it directly, it's blocking
        return Flux.fromStream(() -> this.purchaseOrderRepository.findByUserId(userId).stream()) // blocking, but we can stream it and attach it to a schedulers using subscribeOn()
                .map(EntityDtoMappers::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic()); // scheduler will wait until the sql query complete
    }

}
