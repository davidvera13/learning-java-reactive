package com.grpc.app.server.shared;

import com.grpc.models.WithdrawError;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;

public class ClientConstants {
    public static final Metadata.Key<WithdrawError> WITHDRAWAL_ERROR_KEY = ProtoUtils.keyForProto(WithdrawError.getDefaultInstance());

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
