package com.grpc.app.server.rpctypes.setup;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * faking a database, we can query in the map and get info
 * 1 -> 10$
 * 2 -> 20$
 * 9 -> 90$
 * 10 -> 100$
 */
public class AccountFakeDb {
    private static final Map<Integer, Integer> MAP = IntStream
            .rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), balance -> balance * 100));

    public static int getBalance(int accountId) {
        return MAP.get(accountId);
    }

    public static Integer addBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) -> v + amount);
    }

    public static Integer deductBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) -> v - amount);
    }

    public static void printAccountDetails() {
        System.out.println(MAP);
    }
}
