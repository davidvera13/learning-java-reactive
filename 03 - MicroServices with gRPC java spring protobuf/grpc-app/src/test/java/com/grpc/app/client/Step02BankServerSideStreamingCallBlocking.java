package com.grpc.app.client;

import com.grpc.models.Balance;
import com.grpc.models.BalanceCheckRequest;
import com.grpc.models.BankServiceGrpc;
import com.grpc.models.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step02BankServerSideStreamingCallBlocking {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    @BeforeAll
    public void setup() {
        // we create a channel for communication
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6789)
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    void withdraw() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40)
                .build();
        this.blockingStub.withdraw(withdrawRequest)
                .forEachRemaining(money -> System.out.println("Received " + money.getValue()));
    }

    @Test
    void withdraw_fail() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(4)
                .setAmount(50)
                .build();

        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(4)
                .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);

        StatusRuntimeException thrown = Assertions.assertThrows(
                StatusRuntimeException.class,
                () -> this.blockingStub
                        .withdraw(withdrawRequest)
                        .forEachRemaining(money -> System.out.println("Received " + money.getValue())));

        assertEquals("FAILED_PRECONDITION", thrown.getStatus().getCode().toString());
        assertEquals("FAILED_PRECONDITION: Not enough money .. You only have " + balance.getAmount(), thrown.getMessage());
    }
}
