package com.grpc.app.server;

import com.grpc.models.Balance;
import com.grpc.models.BalanceCheckRequest;
import com.grpc.models.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        // super.getBalance(request, responseObserver);
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(accountNumber * 150) // this should generate 150 for id = 1, 300 for id = 2...
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

}
