package com.grpc.app.client.rpctypes;

import com.grpc.models.Balance;
import com.grpc.models.BalanceCheckRequest;
import com.grpc.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step01BankUnaryCallBlocking {
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
    void getBalanceTest() {
        BalanceCheckRequest balanceCheckRequest;
        Balance balance;

        balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(5)
                .build();
        balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println("Received " + balance.getAmount());

        System.out.println("***************");
        balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(8)
                .build();
        balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println("Received " + balance.getAmount());
    }
}
