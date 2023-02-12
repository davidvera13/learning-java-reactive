package com.grpc.app.server.metadata.service;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.app.server.rpctypes.setup.AccountFakeDb;
import com.grpc.app.server.rpctypes.streaming.DepositStreamingRequest;
import com.grpc.models.*;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class MetaDataService extends BankServiceGrpc.BankServiceImplBase {

    // unary
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountFakeDb.getBalance(accountNumber))
                .build();
        // faking response latency
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    // server-side streaming
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount(); //10, 20, 30..
        int balance = AccountFakeDb.getBalance(accountNumber);

        if(balance < amount){
            Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        // all the validations passed
        for (int i = 0; i < (amount/10); i++) {
            Money money = Money.newBuilder().setValue(10).build();
            //simulate time-consuming call
            Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
            if(!Context.current().isCancelled()){
                responseObserver.onNext(money);
                System.out.println("Delivered $10");
                AccountFakeDb.deductBalance(accountNumber, 10);
            }else{
                break;
            }
        }
        System.out.println("Completed");
        responseObserver.onCompleted();
    }

    // server-side streaming

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new DepositStreamingRequest(responseObserver);
    }
}
