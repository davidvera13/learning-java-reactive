package com.reactive.users.ws.io.repository;

import com.reactive.users.ws.io.entity.UsersEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UsersEntity, Long> {
    @Modifying
    @Query("UPDATE Users SET balance = balance - :amount WHERE id = :userId AND balance >= :amount")
    Mono<Boolean> updateUserBalance(long userId, double amount);
}
