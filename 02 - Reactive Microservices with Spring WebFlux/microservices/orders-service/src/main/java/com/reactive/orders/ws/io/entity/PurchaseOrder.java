package com.reactive.orders.ws.io.entity;

import com.reactive.orders.ws.dto.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;


@Data
@Entity
@ToString
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Long id;
    private String productId;
    private Long userId;
    private Double amount;
    private OrderStatus status;

}