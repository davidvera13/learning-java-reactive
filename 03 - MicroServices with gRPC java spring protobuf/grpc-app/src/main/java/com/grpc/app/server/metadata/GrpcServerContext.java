package com.grpc.app.server.metadata;

import com.grpc.app.server.metadata.service.MetaDataServiceContext;
import com.grpc.app.server.metadata.service.MetaDataServiceSession;
import com.grpc.app.server.metadata.utils.AuthInterceptorContext;
import com.grpc.app.server.metadata.utils.AuthInterceptorSession;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServerContext {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        // client calls are in the test package
        Server server = ServerBuilder.forPort(6789)
                .intercept(new AuthInterceptorContext())
                .addService(new MetaDataServiceContext())
                .build();

        server.start();
        server.awaitTermination();
    }
}
