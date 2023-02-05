package com.grpc.app.server;

import com.grpc.app.server.service.BankService;
import com.grpc.app.server.service.TransferService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        // client calls are in the test package
        Server server = ServerBuilder.forPort(6789)
                .addService(new BankService())
                .addService(new TransferService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
