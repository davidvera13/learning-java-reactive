package com.grpc.app.server.loadbalancing;

import com.grpc.app.server.loadbalancing.service.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Lb2GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(5678)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
