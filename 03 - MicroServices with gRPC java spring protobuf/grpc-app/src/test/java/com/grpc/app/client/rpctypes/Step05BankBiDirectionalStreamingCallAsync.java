package com.grpc.app.client.rpctypes;

import com.grpc.app.client.rpctypes.utils.TransferStreamingResponse;
import com.grpc.models.TransferRequest;
import com.grpc.models.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Step05BankBiDirectionalStreamingCallAsync {
    private TransferServiceGrpc.TransferServiceStub stub;
    @BeforeAll
    public void setup() {
        // we create a channel for communication
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6789)
                .usePlaintext()
                .build();

        // non blocking
        this.stub = TransferServiceGrpc.newStub(managedChannel);
    }

    @Test
    void transferStreamingRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<TransferRequest> transferRequestStreamObserver = this.stub.transfer(new TransferStreamingResponse(countDownLatch));
        for (int i = 0; i < 100; i++) {
            TransferRequest transferRequest = TransferRequest.newBuilder()
                    .setSourceAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setDestinationAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 5))
                    .build();

            transferRequestStreamObserver.onNext(transferRequest);
        }

        transferRequestStreamObserver.onCompleted();
        countDownLatch.await();
    }
}
