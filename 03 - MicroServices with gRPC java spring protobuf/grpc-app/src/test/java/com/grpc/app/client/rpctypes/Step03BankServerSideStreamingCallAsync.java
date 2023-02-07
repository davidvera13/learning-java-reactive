package com.grpc.app.client.rpctypes;

import com.grpc.app.client.rpctypes.utils.MoneyStreamingResponse;
import com.grpc.models.Balance;
import com.grpc.models.BalanceCheckRequest;
import com.grpc.models.BankServiceGrpc;
import com.grpc.models.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step03BankServerSideStreamingCallAsync {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub stub;
    @BeforeAll
    public void setup() {
        // we create a channel for communication
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6789)
                .usePlaintext()
                .build();

        // blocking
        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        // non blocking
        this.stub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    void withdrawAsync() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(8)
                .setAmount(50)
                .build();
        this.stub.withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
        // Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
               // .forEachRemaining(money -> System.out.println("Received " + money.getValue()));
        countDownLatch.await();
    }

    @Test
    void withdraw_fail() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(9)
                .setAmount(100)
                .build();

        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(4)
                .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);

        this.stub
                .withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));

        //RuntimeException thrown = Assertions.assertThrows(
        //        RuntimeException.class,
        //        () -> this.stub
        //        .withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch)));
        // Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
        // assertEquals("FAILED_PRECONDITION", thrown.getCause());
        // assertEquals("FAILED_PRECONDITION: Not enough money .. You only have " + balance.getAmount(), thrown.getMessage());

        countDownLatch.await();
    }
}
