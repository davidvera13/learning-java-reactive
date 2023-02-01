package com.reactive.orders.ws.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {
    private String id;
    private String description;
    private Double price;
}
