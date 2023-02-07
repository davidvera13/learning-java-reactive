package com.grpc.app.server.rpctypes.service;

import com.grpc.app.server.rpctypes.setup.AccountFakeDb;
import com.grpc.app.server.rpctypes.streaming.TransferStreamingRequest;
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
