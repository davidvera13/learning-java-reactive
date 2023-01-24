package com.reactive.sec12Context.helper;

import com.reactive.utils.Utils;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BookService {
    private static final Map<String, Integer> map = new HashMap<>();

    static {
        map.put("standard", 2);
        map.put("prime", 3);
    }

    public static Mono<String> getBook() {
        return Mono
                .deferContextual(ctx -> {
                    if(ctx.get("allow"))
                        return Mono.just(Utils.faker().book().title());
                    else
                        return Mono.error(new RuntimeException("Oops, you're not allowed"));
                })
                .contextWrite(rateLimiterContext());
    }

    private static Function<Context, Context> rateLimiterContext() {
        return ctx -> {
          if(ctx.hasKey("category")) {
              String category = ctx.get("category").toString();
              Integer attempts = map.get(category);
              if(attempts > 0) {
                  map.put(category, attempts - 1);
                  return ctx.put("allow", true);
              }
          }
          return ctx.put("allow", false);
        };
    }
}
