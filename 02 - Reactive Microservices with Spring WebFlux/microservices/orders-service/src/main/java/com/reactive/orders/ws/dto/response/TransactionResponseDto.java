package com.reactive.orders.ws.dto.response;

import com.reactive.orders.ws.dto.enums.TransactionStatus;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class TransactionResponseDto {
    private Long userId;
    private Double amount;
    private TransactionStatus status;
}
