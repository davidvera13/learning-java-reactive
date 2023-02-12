package com.grpc.app.server.metadata.utils;

import com.grpc.app.server.shared.ServerConstants;
import io.grpc.*;

import java.util.Objects;

/**
 * let's consider that token with value user-token-ok-1 and user-token-ok-3 are both valid
 * user-token-ok-1:basic
 * user-token-ok-3:prime
 */
public class AuthInterceptorContext implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.USER_TOKEN);
        System.out.println(token);

        if(this.isTokenValid(token)) {
            UserRole userRole = this.extractUserRole(token);
            Context context = Context.current().withValue(ServerConstants.CTX_USER_ROLE, userRole);
            return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
           // return serverCallHandler.startCall(serverCall, metadata);
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
        System.out.println("isTokenValid ?"  + token);
        // hard coded
        return Objects.nonNull(token)  && (token.startsWith("user-token-ok-1") || token.startsWith("user-token-ok-3"));
    }

    private UserRole extractUserRole(String jwt) {
        return  jwt.endsWith("prime") ? UserRole.PRIME: UserRole.BASIC;
    }

}
