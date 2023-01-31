package com.reactive.users.ws.io.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@ToString
@Builder
// not using "entity" -> not using hibernate annotations
// table is spring data annotation
@Table(name="transactions")
public class UserTransactionsEntity {

    // spring data annotation
    @Id
    private Long id;
    // we define FK here manually
    private Long userId;
    private Double amount;
    private LocalDateTime transactionData;
}
