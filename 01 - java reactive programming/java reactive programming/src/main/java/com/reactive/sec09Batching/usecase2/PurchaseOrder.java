package com.reactive.sec09Batching.usecase2;

import com.reactive.utils.Utils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private double price;
    private String category;

    public PurchaseOrder() {
        this.item = Utils.faker().commerce().productName();
        this.price = Double.parseDouble(Utils.faker().commerce().price().replace(",", "."));
        this.category = Utils.faker().commerce().department();
    }
}
