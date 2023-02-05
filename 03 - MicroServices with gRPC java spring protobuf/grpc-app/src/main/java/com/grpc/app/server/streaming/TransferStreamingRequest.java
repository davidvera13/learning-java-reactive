package com.grpc.app.server.streaming;

import com.grpc.app.server.setup.AccountFakeDb;
import com.grpc.models.Account;
import com.grpc.models.TransferRequest;
import com.grpc.models.TransferResponse;
import com.grpc.models.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {
    private StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int sourceAccount = transferRequest.getSourceAccount();
        int destinationAccount = transferRequest.getDestinationAccount();
        int amount = transferRequest.getAmount();
        // check balance
        int balance = AccountFakeDb.getBalance(sourceAccount);

        // by default staus is failed
        TransferStatus status = TransferStatus.FAILED;
        if(balance > amount && sourceAccount != destinationAccount) {
            AccountFakeDb.deductBalance(sourceAccount, amount);
            AccountFakeDb.addBalance(destinationAccount, amount);
            status = TransferStatus.SUCCESS;
        }
        TransferResponse transferResponse = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(Account.newBuilder()
                        .setAccountNumber(sourceAccount)
                        .setAmount(AccountFakeDb.getBalance(sourceAccount))
                        .build())
                .addAccounts(Account.newBuilder()
                        .setAccountNumber(destinationAccount)
                        .setAmount(AccountFakeDb.getBalance(destinationAccount))
                        .build())
                .build();

        this.transferResponseStreamObserver.onNext(transferResponse);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        AccountFakeDb.printAccountDetails();
        this.transferResponseStreamObserver.onCompleted();
    }
}
