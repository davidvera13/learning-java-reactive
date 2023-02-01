package com.reactive.orders.ws.dto.request;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class TransactionRequestDto {
    private Long userId;
    private Double amount;
}
