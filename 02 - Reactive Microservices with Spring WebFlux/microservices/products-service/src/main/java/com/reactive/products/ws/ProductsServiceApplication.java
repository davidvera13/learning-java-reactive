package com.reactive.products.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.reactive.products.ws.io.repository")
public class ProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }

}
