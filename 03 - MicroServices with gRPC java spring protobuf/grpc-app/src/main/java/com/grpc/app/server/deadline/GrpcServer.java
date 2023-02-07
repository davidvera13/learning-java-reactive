package com.grpc.app.server.deadline;

import com.grpc.app.server.deadline.service.DeadlineService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // creating server
        // client calls are in the test package
        Server server = ServerBuilder.forPort(6789)
                .addService(new DeadlineService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
