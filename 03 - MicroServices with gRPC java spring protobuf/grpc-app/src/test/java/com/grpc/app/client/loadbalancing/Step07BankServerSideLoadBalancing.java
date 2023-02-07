package com.grpc.app.client.loadbalancing;

import com.grpc.app.client.rpctypes.utils.BalanceStreamObserver;
import com.grpc.app.client.rpctypes.utils.MoneyStreamingResponse;
import com.grpc.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step07BankServerSideLoadBalancing {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    // private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        // we create a channel for communication
         ManagedChannel managedChannel = ManagedChannelBuilder
                 .forAddress("localhost", 8585)
                .usePlaintext()
                .build();

        // non blocking
        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
       // this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void balanceTest() throws InterruptedException {
//        for (int i = 1; i < 11; i++) {
//            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
//                    .setAccountNumber(i)
//                    .build();
        for (int i = 1; i < 100; i++) {
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1, 11))
                    .build();
            try {
                Thread.sleep(375);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
            System.out.println(
                    "Received : " + balance.getAmount()
            );
        }
    }

    @Test
    public void withdrawTest(){
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
        this.blockingStub.withdraw(withdrawRequest)
                .forEachRemaining(money -> System.out.println("Received : " + money.getValue()));
    }

}
