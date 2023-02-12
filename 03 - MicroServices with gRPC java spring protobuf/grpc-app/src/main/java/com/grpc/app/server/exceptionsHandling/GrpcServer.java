package com.grpc.app.server.exceptionsHandling;

import com.grpc.app.server.exceptionsHandling.service.MetaDataService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6789)
                .addService(new MetaDataService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
