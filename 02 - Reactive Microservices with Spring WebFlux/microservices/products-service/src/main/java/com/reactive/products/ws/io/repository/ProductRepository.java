package com.reactive.products.ws.io.repository;

import com.reactive.products.ws.io.entity.Products;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Products, String> {

}
