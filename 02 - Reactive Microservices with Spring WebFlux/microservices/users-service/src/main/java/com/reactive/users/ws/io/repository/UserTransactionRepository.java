package com.reactive.users.ws.io.repository;

import com.reactive.users.ws.io.entity.UserTransactionsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransactionsEntity, Long> {
}
