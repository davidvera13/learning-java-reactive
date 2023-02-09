package com.grpc.app.server.interceptor.utils;

import io.grpc.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DeadlineInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor,
                                                               CallOptions callOptions,
                                                               Channel channel) {
        Deadline deadline = callOptions.getDeadline();
        if(Objects.isNull(deadline))
            // if deadline is not set, we use the configured one
            callOptions = callOptions.withDeadline(Deadline.after(2, TimeUnit.SECONDS));
        // do nothing at all ///
        // return channel.newCall(methodDescriptor, callOptions);
        //return channel.newCall(methodDescriptor, callOptions.withDeadline(Deadline.after(4, TimeUnit.SECONDS)));
        return channel.newCall(methodDescriptor, callOptions);
    }
}
