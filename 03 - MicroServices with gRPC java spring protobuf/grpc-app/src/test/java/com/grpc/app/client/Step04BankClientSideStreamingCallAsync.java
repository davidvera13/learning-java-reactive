package com.grpc.app.client;

import com.grpc.app.client.utils.BalanceStreamObserver;
import com.grpc.app.client.utils.MoneyStreamingResponse;
import com.grpc.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step04BankClientSideStreamingCallAsync {
    private BankServiceGrpc.BankServiceStub stub;
    @BeforeAll
    public void setup() {
        // we create a channel for communication
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6789)
                .usePlaintext()
                .build();

        // non blocking
        this.stub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    void cashStreamingRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<DepositRequest> depositRequestStreamObserver = this.stub.cashDeposit(new BalanceStreamObserver(countDownLatch));
        for (int i = 0; i < 10; i++) {
            int deposit = (int) (100 * Math.random());
            System.out.println("deposit #" + (i + 1) + " = " + deposit);
            DepositRequest depositRequest = DepositRequest.newBuilder()
                    .setAccountNumber(8)
                    .setAmount(deposit)
                    .build();
            depositRequestStreamObserver.onNext(depositRequest);
        }

        depositRequestStreamObserver.onCompleted();
        countDownLatch.await();
    }
}
