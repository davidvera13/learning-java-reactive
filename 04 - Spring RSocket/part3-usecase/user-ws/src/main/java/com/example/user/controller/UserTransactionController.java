package com.example.user.controller;


import com.example.user.domain.TransactionRequest;
import com.example.user.domain.TransactionResponse;
import com.example.user.services.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("users")
public class UserTransactionController {
    private final UserTransactionService userTransactionService;


    @Autowired
    public UserTransactionController(UserTransactionService userTransactionService) {
        this.userTransactionService = userTransactionService;
    }

    @MessageMapping("transactions")
    public Mono<TransactionResponse> doTransaction(Mono<TransactionRequest> transactionRequestMono) {
        return transactionRequestMono
                .flatMap(this.userTransactionService::doTransaction);
    }
}
