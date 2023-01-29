package com.reactive.products.ws.io.entity;

import jdk.jfr.Enabled;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Data
@ToString
//@Entity(name = "products")
public class Products {
    @Id
    private String id;
    private String description;
    private Double price;
}
