package com.example.trading.domain;

import com.example.trading.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor @NoArgsConstructor
public class TransactionRequest {
    private String userId;
    private float amount;
    private TransactionType type;
}
