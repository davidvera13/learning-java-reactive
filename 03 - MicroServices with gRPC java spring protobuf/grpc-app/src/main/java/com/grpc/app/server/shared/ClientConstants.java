package com.grpc.app.server.shared;

import io.grpc.Metadata;

public class ClientConstants {
    public static final Metadata.Key<String> USER_TOKEN = Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata METADATA = new Metadata();


    static {
        // we can even send proto files
        METADATA.put(
                Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                "bank-client-secret"
        );
    }
    public static Metadata getClientToken() {
        return METADATA;
    }
}
