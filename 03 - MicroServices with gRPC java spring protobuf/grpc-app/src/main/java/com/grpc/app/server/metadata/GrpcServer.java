package com.grpc.app.server.metadata;

import com.grpc.app.server.metadata.service.MetaDataService;
import com.grpc.app.server.metadata.utils.AuthInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        // client calls are in the test package
        Server server = ServerBuilder.forPort(6789)
                .intercept(new AuthInterceptor())
                .addService(new MetaDataService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
