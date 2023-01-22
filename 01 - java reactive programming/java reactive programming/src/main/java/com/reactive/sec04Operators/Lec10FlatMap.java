package com.reactive.sec04Operators;

import com.reactive.sec04Operators.helper.OrderService;
import com.reactive.sec04Operators.helper.UserService;
import com.reactive.utils.Utils;

public class Lec10FlatMap {
    public static void main(String[] args) {
        UserService.getUsers()
                .map(user -> OrderService.getOrders(user.getUserId()))
                .subscribe(Utils.subscriber());

        System.out.println("********************");

        UserService.getUsers()
                .flatMap(user -> OrderService.getOrders(user.getUserId())) // mono / flux
                .filter(p -> Double.parseDouble(p.getPrice().replace(",", ".")) > 80)
                .subscribe(Utils.subscriber());

    }
}
