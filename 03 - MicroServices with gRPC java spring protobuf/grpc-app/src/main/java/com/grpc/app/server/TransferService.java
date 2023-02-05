package com.grpc.app.server;

import com.grpc.models.TransferRequest;
import com.grpc.models.TransferResponse;
import com.grpc.models.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        AccountFakeDb.printAccountDetails();
        // return super.transfer(responseObserver);
        return new TransferStreamingRequest(responseObserver);
    }
}
