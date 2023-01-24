package com.reactive.sec12Context;

import com.reactive.sec12Context.helper.BookService;
import com.reactive.sec12Context.helper.UserService;
import com.reactive.utils.Utils;
import reactor.util.context.Context;

public class Lec02RateLimiterWithContext {
    public static void main(String[] args) {
        // not allowed
        BookService
                .getBook()
                .subscribe(Utils.subscriber());

        BookService
                .getBook()
                .repeat(2)
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "walter"))
                .subscribe(Utils.subscriber());

        System.out.println("*********************");

        BookService
                .getBook()
                .repeat(3)
                .contextWrite(UserService.userCategoryContext())
                .contextWrite(Context.of("user", "fox"))
                .subscribe(Utils.subscriber());
    }
}
