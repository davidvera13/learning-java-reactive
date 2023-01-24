package com.reactive.sec12Context.helper;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.Function;

public class UserService {
    private static Map<String, String> MAP = Map.of(
            "walter", "standard",
            "fox", "prime"
    );

    public static Function<Context, Context> userCategoryContext() {
        return ctx -> {
            String userName = ctx.get("user").toString();
            String category = MAP.get(userName);
            return ctx.put("category", category);
        };
    }
}
