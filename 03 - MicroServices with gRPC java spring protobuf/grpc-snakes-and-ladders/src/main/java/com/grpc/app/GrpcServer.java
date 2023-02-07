package com.grpc.app;

import com.grpc.app.service.GameService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        Server server = ServerBuilder.forPort(6789)
                .addService(new GameService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
