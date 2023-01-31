package com.reactive.users.ws.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class TransactionRequestDto {
    private Long userId;
    private Double amount;
}
