package com.example.trading.io.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "users-stocks")
public class UserStockEntity {
    private String id;
    private String userId;
    private String stockSymbol;
    private Integer quantity;
}
