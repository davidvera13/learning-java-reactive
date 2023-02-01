package com.reactive.orders.ws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor @AllArgsConstructor
public class PurchaseOrderRequestDto {

    private Long userId;
    private String productId;

}
