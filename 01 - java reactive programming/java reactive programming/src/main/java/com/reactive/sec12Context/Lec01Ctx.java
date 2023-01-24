package com.reactive.sec12Context;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Lec01Ctx {
    public static void main(String[] args) {
        getWelcomeMessage()
                // read from bottom to top
                .contextWrite(ctx -> ctx.put("user", ctx.get("user").toString().toUpperCase()))
                .contextWrite(Context.of(
                         "user", "Holmes",
                         "clientId", "123456"
                ))
                // .contextWrite(Context.of(
                //         "user", "Holmes",
                //         "clientId", "123456"
                //))
                // .contextWrite(Context.of("user", "Moriarty"))
                .subscribe(Utils.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        // return Mono.fromSupplier(() -> "Hello world!");
        return Mono.deferContextual(ctx -> {
            if(ctx.hasKey("clientId"))
                System.out.println("Client id is" + ctx.get("clientId"));
            if(ctx.hasKey("user"))
                return Mono.just("Hello " + ctx.get("user"));
            else
                return Mono.error(new RuntimeException("Opps user name is required"));
        });
    }
}
