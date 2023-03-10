package com.grpc.app.server.loadbalancing.streaming;

import com.grpc.app.server.rpctypes.setup.AccountFakeDb;
import com.grpc.models.Balance;
import com.grpc.models.DepositRequest;
import io.grpc.stub.StreamObserver;

public class DepositStreamingRequest implements StreamObserver<DepositRequest> {
    private StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public DepositStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        System.out.println("Received cash deposit for account #" + accountNumber);
        int amount = depositRequest.getAmount();
        this.accountBalance = AccountFakeDb.addBalance(accountNumber, amount);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Oops, Something went wrong");
    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
        this.balanceStreamObserver.onNext(balance);
        this.balanceStreamObserver.onCompleted();
    }
}
