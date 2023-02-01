package com.reactive.orders.ws.dto.response;

import com.reactive.orders.ws.dto.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor @AllArgsConstructor
public class PurchaseOrderResponseDto {
    private Long orderId;
    private Long userId;
    private String productId;
    private double amount;
    private OrderStatus status;
}
