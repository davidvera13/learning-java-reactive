package com.example.user.services;

import com.example.user.domain.TransactionRequest;
import com.example.user.domain.TransactionResponse;
import reactor.core.publisher.Mono;

public interface UserTransactionService {

    Mono<TransactionResponse> doTransaction(TransactionRequest request);
}
