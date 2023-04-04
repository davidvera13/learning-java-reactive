package com.example.user.controller;

import com.example.user.domain.TransactionRequest;
import com.example.user.domain.TransactionResponse;
import com.example.user.domain.UserDto;
import com.example.user.domain.enums.TransactionStatus;
import com.example.user.domain.enums.TransactionType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTransactionControllerTest {

    private RSocketRequester requester;
    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    void setup() {
        this.requester = this.builder
                .transport(TcpClientTransport
                        .create("localhost", 7071));
    }

    @ParameterizedTest
    @MethodSource("testTransactions")
    void doTransaction(float amount, TransactionType type, TransactionStatus status) {
        UserDto userDto = getRandomUser();
        TransactionRequest request = new TransactionRequest(
                userDto.getId(),
                amount,
                type);

        Mono<TransactionResponse> mono = this.requester
                .route("users.transactions")
                .data(request)
                .retrieveMono(TransactionResponse.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextMatches(transactionResponse -> transactionResponse.getStatus().equals(status))
                .verifyComplete();
    }

    private Stream<Arguments> testTransactions() {
        return Stream.of(
                Arguments.of(2000, TransactionType.CREDIT, TransactionStatus.COMPLETED),
                Arguments.of(1200000, TransactionType.DEBIT, TransactionStatus.FAILED));
    }

    private UserDto getRandomUser() {
        return this.requester.route("users.get.all")
                .retrieveFlux(UserDto.class)
                .next()
                .block();
    }
}