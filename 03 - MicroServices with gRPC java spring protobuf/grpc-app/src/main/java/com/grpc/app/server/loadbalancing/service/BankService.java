package com.grpc.app.server.loadbalancing.service;

import com.grpc.app.server.loadbalancing.streaming.DepositStreamingRequest;
import com.grpc.app.server.rpctypes.setup.AccountFakeDb;
import com.grpc.models.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    // unary
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        System.out.println("# Received request for account #" + accountNumber);
        Balance balance = Balance.newBuilder()
                .setAmount(AccountFakeDb.getBalance(accountNumber)) // this should generate 150 for id = 1, 300 for id = 2...
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    // server-side streaming
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount(); // must be 10, 20, 30...

        int balance = AccountFakeDb.getBalance(accountNumber);

        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough money .. You only have " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        for (int i = 0; i < (amount / 10); i++) {
            Money money = Money.newBuilder()
                    .setValue(10)
                    .build();
            responseObserver.onNext(money);
            AccountFakeDb.deductBalance(accountNumber, 10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        // return super.cashDeposit(responseObserver);
        return new DepositStreamingRequest(responseObserver);
    }
}
