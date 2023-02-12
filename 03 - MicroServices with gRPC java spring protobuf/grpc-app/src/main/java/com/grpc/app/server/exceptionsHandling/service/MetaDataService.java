package com.grpc.app.server.exceptionsHandling.service;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.app.server.rpctypes.setup.AccountFakeDb;
import com.grpc.app.server.rpctypes.streaming.DepositStreamingRequest;
import com.grpc.models.*;
import io.grpc.Context;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
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
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    // server-side streaming
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount(); //10, 20, 30..
        int balance = AccountFakeDb.getBalance(accountNumber);

        if(amount < 10 || amount % 10 != 0) {
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawError> errorKey = ProtoUtils.keyForProto(WithdrawError.getDefaultInstance());
            WithdrawError withdrawError = WithdrawError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.ONLY_TEN_MULTIPLES).build();

            metadata.put(errorKey, withdrawError);

            // Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only " + balance);
            responseObserver.onError(Status.FAILED_PRECONDITION.asRuntimeException(metadata));
            return;
        }

        if(balance < amount){
            Metadata metadata = new Metadata();
            Metadata.Key<WithdrawError> errorKey = ProtoUtils.keyForProto(WithdrawError.getDefaultInstance());
            WithdrawError withdrawError = WithdrawError.newBuilder().setAmount(balance).setErrorMessage(ErrorMessage.INSUFFICIENT_BALANCE).build();

            metadata.put(errorKey, withdrawError);

            // Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only " + balance);
            responseObserver.onError(Status.FAILED_PRECONDITION.asException(metadata));
            return;
        }
        // all the validations passed
        for (int i = 0; i < (amount/10); i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            System.out.println("Delivered $10");
            AccountFakeDb.deductBalance(accountNumber, 10);
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
