package com.reactive.users.ws.io.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@ToString
@Builder
@AllArgsConstructor @NoArgsConstructor
// not using "entity" -> not using hibernate annotations
// table is spring data annotation
@Table(name="transactions")
@EnableR2dbcAuditing
public class UserTransactionsEntity {

    // spring data annotation
    @Id
    private Long id;
    // we define FK here manually
    private Long userId;
    private Double amount;
    @CreatedDate
    private LocalDateTime transactionDate;
}
