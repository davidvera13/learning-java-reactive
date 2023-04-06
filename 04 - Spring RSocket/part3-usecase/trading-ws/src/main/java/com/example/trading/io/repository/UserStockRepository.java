package com.example.trading.io.repository;

import com.example.trading.io.entity.UserStockEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserStockRepository extends ReactiveMongoRepository<UserStockEntity, String> {
    Mono<UserStockEntity> findUserStockEntitiesByUserIdAndStockSymbol(String userId, String stockSymbol);
    Flux<UserStockEntity> findByUserId(String userId);
}
