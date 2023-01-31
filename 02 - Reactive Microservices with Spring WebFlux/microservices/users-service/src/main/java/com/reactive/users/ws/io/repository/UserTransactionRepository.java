package com.reactive.users.ws.io.repository;

import com.reactive.users.ws.io.entity.UserTransactionsEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionsEntity, Long> {
    Flux<UserTransactionsEntity> findAllByUserId(long userId);
}
