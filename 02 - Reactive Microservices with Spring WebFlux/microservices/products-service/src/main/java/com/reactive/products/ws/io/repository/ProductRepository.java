package com.reactive.products.ws.io.repository;

import com.reactive.products.ws.io.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    // Flux<Product> findAllByPriceBetween(int min, int max);
    Flux<Product> findAllByPriceBetween(Range<Integer> range);

}
