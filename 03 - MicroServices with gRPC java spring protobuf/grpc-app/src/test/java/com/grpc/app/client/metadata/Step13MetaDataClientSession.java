package com.grpc.app.client.metadata;

import com.grpc.app.client.rpctypes.utils.BalanceStreamObserver;
import com.grpc.app.client.rpctypes.utils.MoneyStreamingResponse;
import com.grpc.app.server.shared.ClientConstants;
import com.grpc.models.*;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step13MetaDataClientSession {
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        // we create a channel for communication
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 6789)
                .intercept(
                        MetadataUtils
                                .newAttachHeadersInterceptor(ClientConstants.getClientToken()))
                .usePlaintext()
                .build();

        this.blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void balanceTest() throws InterruptedException {
        int random = ThreadLocalRandom.current().nextInt(1,3);
        System.out.println(random);
        // thread sleeping
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(7)
                .build();
        try {
            Balance balance = this.blockingStub
                    .withCallCredentials(new UserSessionToken("user-token-ok-" + random))
                    .getBalance(balanceCheckRequest);
            System.out.println(
                    "Received : " + balance.getAmount()
            );
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void withdrawTest(){
        int random = ThreadLocalRandom.current().nextInt(1,3);
        System.out.println(random);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(6)
                .setAmount(50)
                .build();
        try{
            this.blockingStub
                    .withCallCredentials(new UserSessionToken("user-token-ok-" + random))
                    .withDeadline(Deadline.after(7, TimeUnit.SECONDS))
                    .withdraw(withdrawRequest)
                    .forEachRemaining(money -> System.out.println("Received : " + money.getValue()));
        }catch (StatusRuntimeException e){
            //
        }

    }

    @Test
    public void withdrawAsyncTest() throws InterruptedException {
        int random = ThreadLocalRandom.current().nextInt(1,3);
        System.out.println(random);
        CountDownLatch latch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(60)
                .build();
        this.bankServiceStub
                .withCallCredentials(new UserSessionToken("user-token-ok-" + random))
                .withdraw(withdrawRequest, new MoneyStreamingResponse(latch));
        latch.await();
    }

    @Test
    public void cashStreamingRequest() throws InterruptedException {
        int random = ThreadLocalRandom.current().nextInt(1,3);
        System.out.println(random);
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<DepositRequest> streamObserver = this.bankServiceStub
                .withCallCredentials(new UserSessionToken("user-token-ok-" + random))
                .cashDeposit(new BalanceStreamObserver(latch));
        for (int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
            streamObserver.onNext(depositRequest);
        }
        streamObserver.onCompleted();
        latch.await();
    }

}
