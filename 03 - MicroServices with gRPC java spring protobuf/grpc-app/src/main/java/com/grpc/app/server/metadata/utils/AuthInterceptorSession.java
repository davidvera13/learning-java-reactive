package com.grpc.app.server.metadata.utils;

import com.grpc.app.server.shared.ServerConstants;
import io.grpc.*;

import java.util.Objects;

public class AuthInterceptorSession implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.USER_TOKEN);
        if(this.isTokenValid(token)) {
           return serverCallHandler.startCall(serverCall, metadata);
        }
        Status status = Status.UNAUTHENTICATED.withDescription("Invalid token");
        serverCall.close(status, metadata);
        return new ServerCall.Listener<ReqT>() {
            @Override
            public void onMessage(ReqT message) {
                super.onMessage(message);
            }
        };
    }

    private boolean isTokenValid(String token) {
        // hard coded
        return Objects.nonNull(token)  && token.equals("user-token-ok-1");
    }


}
