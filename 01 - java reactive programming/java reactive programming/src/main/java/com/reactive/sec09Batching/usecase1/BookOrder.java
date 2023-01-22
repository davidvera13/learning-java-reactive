package com.reactive.sec09Batching.usecase1;

import com.github.javafaker.Book;
import com.reactive.utils.Utils;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BookOrder {
    private String title;
    private String author;
    private String category;
    private double price;

    public BookOrder() {
        Book book = Utils.faker().book();
        this.author = book.author();
        this.category = book.genre();
        this.title = book.title();
        this.price = Double.parseDouble(Utils.faker().commerce().price().replace(",", "."));
    }
}
