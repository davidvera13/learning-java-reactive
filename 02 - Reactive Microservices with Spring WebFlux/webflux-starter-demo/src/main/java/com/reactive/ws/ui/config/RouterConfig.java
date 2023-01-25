package com.reactive.ws.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    private final RequestHandler requestHandler;

    @Autowired
    public RouterConfig(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/multiplication/{input}", requestHandler::tableHandler)
                .GET("router/multiplication/{input}/stream", requestHandler::tableStreamingHandler)
                .POST("router/operation", requestHandler::doOperationHandler)
                .build();
    }
}
