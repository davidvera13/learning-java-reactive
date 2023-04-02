package com.example.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Getter @Setter
public class Stock {
    private float price;
    private String code;
    private final int volatility;

    public float getPrice() {
        this.updatePrice();
        return price;
    }

    private void updatePrice() {
        float rand = ThreadLocalRandom.current().nextFloat(- 1 + volatility, volatility + 1);
        this.price = rand + this.price;
        this.price = Math.max(this.price, 0); // price can't be negative
    }
}
