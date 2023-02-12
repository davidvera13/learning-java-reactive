package com.grpc.app.server.exceptionsHandling.utils;

import com.grpc.app.server.shared.ServerConstants;
import io.grpc.*;

import java.util.Objects;

public class AuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.TOKEN);
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
        return Objects.nonNull(token)  && token.equals("bank-client-secret");
    }


}
