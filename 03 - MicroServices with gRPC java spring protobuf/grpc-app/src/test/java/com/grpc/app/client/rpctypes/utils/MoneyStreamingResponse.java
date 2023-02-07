package com.grpc.app.client.rpctypes.utils;

import com.grpc.models.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class MoneyStreamingResponse implements StreamObserver<Money> {
    private CountDownLatch countDownLatch;

    public MoneyStreamingResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println("Received async : " + money.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        countDownLatch.countDown();
        // throw new RuntimeException(String.valueOf(Status.FAILED_PRECONDITION.withDescription(throwable.getMessage())));

    }

    @Override
    public void onCompleted() {
        System.out.println("Completed!");
        countDownLatch.countDown();
    }
}
