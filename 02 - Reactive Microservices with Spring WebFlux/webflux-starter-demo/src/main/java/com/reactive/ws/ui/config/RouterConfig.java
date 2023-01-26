package com.reactive.ws.ui.config;

import com.reactive.ws.ui.dto.ErrorResponse;
import com.reactive.ws.ui.exceptions.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

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
                .path("customUrl", this::serverResponseCustomUrlRouterFunction)
                .GET("router/square/{input}", requestHandler::squareHandler)
                .GET("router/multiplication/{input}", requestHandler::tableHandler)
                .GET("router/multiplication/{input}/stream", requestHandler::tableStreamingHandler)
                .POST("router/operation", requestHandler::doOperationHandler)
                .GET("router/squareWithValidation/{input}", requestHandler::squareWithValidationHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    // create sub "routes: beans declaration is not required
    private RouterFunction<ServerResponse> serverResponseCustomUrlRouterFunction() {
        /// the routes will start with /customUrl
        return RouterFunctions.route()
                // using request predicate: we pass a pattern, number should start with 1 and have a second number from 1 to 9
                // url must contain 10, 11, 12...19, if patterns is ok, the request handler method is called
                .GET(
                        "square/{input}",
                        RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")),
                        requestHandler::squareHandler)
                // if pattern is not followed...
                .GET(
                        "square/{input}",
                        request -> ServerResponse.badRequest().bodyValue("Only 10 - 19 allowed"))
                .GET("multiplication/{input}", requestHandler::tableHandler)
                .GET("multiplication/{input}/stream", requestHandler::tableStreamingHandler)
                .POST("operation", requestHandler::doOperationHandler)
                .GET("squareWithValidation/{input}", requestHandler::squareWithValidationHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }




    // we can have as many handler ... we must declare bean every time
    @Bean
    public RouterFunction<ServerResponse> serverResponseOtherRouterFunction() {
        return RouterFunctions.route()
                .GET("other/square/{input}", requestHandler::squareHandler)
                .GET("other/multiplication/{input}", requestHandler::tableHandler)
                .GET("other/multiplication/{input}/stream", requestHandler::tableStreamingHandler)
                .POST("other/operation", requestHandler::doOperationHandler)
                .GET("other/squareWithValidation/{input}", requestHandler::squareWithValidationHandler)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }


    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler()  {
        // we use error and request
        return (err, req) ->{
            InputValidationException exception = (InputValidationException) err;
            ErrorResponse response = new ErrorResponse();
            response.setInput(exception.getInput());
            response.setMessage(exception.getMessage());
            response.setErrorCode(exception.getErrorCode());

            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
