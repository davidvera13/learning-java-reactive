package com.grpc.app.server.metadata;

import com.grpc.app.server.metadata.service.MetaDataService;
import com.grpc.app.server.metadata.service.MetaDataServiceSession;
import com.grpc.app.server.metadata.utils.AuthInterceptor;
import com.grpc.app.server.metadata.utils.AuthInterceptorSession;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServerSession {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        // client calls are in the test package
        Server server = ServerBuilder.forPort(6789)
                .intercept(new AuthInterceptorSession())
                .addService(new MetaDataServiceSession())
                .build();

        server.start();
        server.awaitTermination();
    }
}
