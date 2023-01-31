package com.reactive.users.ws.dto;

import com.reactive.users.ws.dto.enums.TransactionStatus;
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
