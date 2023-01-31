package com.reactive.users.ws.service;

import com.reactive.users.ws.dto.TransactionRequestDto;
import com.reactive.users.ws.dto.TransactionResponseDto;
import com.reactive.users.ws.dto.enums.TransactionStatus;
import com.reactive.users.ws.io.repository.UserRepository;
import com.reactive.users.ws.io.repository.UserTransactionRepository;
import com.reactive.users.ws.mappers.EntityDtoMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final UserTransactionRepository userTransactionRepository;

    @Autowired
    public TransactionService(UserRepository userRepository, UserTransactionRepository userTransactionRepository) {
        this.userRepository = userRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    public Mono<TransactionResponseDto> create(TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)   // we proceed if we were able to update balance value
                .map(bool -> EntityDtoMappers.transactionDtoToUserTransactionEntity(requestDto))    // we convert the request dto to entity object
                // for debug ...
                //.flatMap(userTransactionsEntity ->  this.userTransactionRepository.save(userTransactionsEntity))
                .flatMap(this.userTransactionRepository::save) // we have to persist the userTransaction entity
                .map(userTransactionsEntity -> EntityDtoMappers.transactionRequestDtoToTransactionResponseDto(requestDto, TransactionStatus.APPROVED))// buiding response if OK
                .defaultIfEmpty(EntityDtoMappers.transactionRequestDtoToTransactionResponseDto(requestDto, TransactionStatus.DECLINED)); // building negative response
    }

    public Flux<TransactionResponseDto> findAllByUserId(long userId) {
        return this.userTransactionRepository.findAllByUserId(userId)
                .map(userTransactionsEntity -> EntityDtoMappers.transactionEntityToTransactionDto(userTransactionsEntity));
    }
}
