package com.example.user.services.impl;

import com.example.user.domain.TransactionRequest;
import com.example.user.domain.TransactionResponse;
import com.example.user.domain.enums.TransactionStatus;
import com.example.user.domain.enums.TransactionType;
import com.example.user.io.entity.UserEntity;
import com.example.user.io.repository.UserRepository;
import com.example.user.services.UserTransactionService;
import com.example.user.shared.utils.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@Service
public class UserTransactionServiceImpl implements UserTransactionService {
    private final UserRepository userRepository;


    @Autowired
    public UserTransactionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public  Mono<TransactionResponse> doTransaction(TransactionRequest request) {
        UnaryOperator<Mono<UserEntity>> operation = TransactionType.CREDIT
                .equals(request.getType()) ? credit(request) : debit(request);

        return this.userRepository.findById(request.getUserId())
                .transform(operation)
                .flatMap(this.userRepository::save)
                .map(transactionResponse -> TransactionMapper.toResponse(request, TransactionStatus.COMPLETED))
                .defaultIfEmpty(TransactionMapper.toResponse(request, TransactionStatus.FAILED));
    }

    // transform function - unary operator
    private UnaryOperator<Mono<UserEntity>> credit(TransactionRequest request) {
        return userEntityMono -> userEntityMono
                .doOnNext(userEntity -> userEntity.setBalance(userEntity.getBalance() + request.getAmount()));
    }

    private UnaryOperator<Mono<UserEntity>> debit(TransactionRequest request) {
        return userEntityMono -> userEntityMono
                .filter(userEntity -> userEntity.getBalance() >= request.getAmount())
                .doOnNext(userEntity -> userEntity.setBalance(userEntity.getBalance() - request.getAmount()));
    }
}
