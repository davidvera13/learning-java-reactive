package com.grpc.app.server.shared;

import com.grpc.app.server.metadata.utils.UserRole;
import io.grpc.*;

import java.util.Objects;

public class ServerConstants implements ServerInterceptor {
    public static final Metadata.Key<String> TOKEN =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> USER_TOKEN =
            Metadata.Key.of("user-token", Metadata.ASCII_STRING_MARSHALLER);

    public static final Context.Key<UserRole> CTX_USER_ROLE = Context.key("user-role");


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.TOKEN);
        if(this.validate(token)) {
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid / expired token");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<ReqT>() {
            @Override
            public void onMessage(ReqT message) {
                super.onMessage(message);
            }
        };
    }

    private boolean validate(String token) {
        return Objects.nonNull(token) && token.equals("bank-client-secret");
    }
}
