package com.reactive.sec04Operators.helper;

import com.reactive.utils.Utils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {
    private int userId;
    private String item;
    private String price;

    public PurchaseOrder(int userId) {
        this.userId = userId;
        this.item = Utils.faker().commerce().productName();
        this.price = Utils.faker().commerce().price();
    }
}
