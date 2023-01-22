package com.reactive.sec05HotColdPublishers.usecase;

import com.reactive.utils.Utils;

public class App {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        RevenueService revenueService = new RevenueService();
        InventoryService inventoryService = new InventoryService();

        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

        inventoryService.inventoryStream().subscribe(Utils.subscriber("inventory"));
        revenueService.revenueStream().subscribe(Utils.subscriber("revenue"));

        Utils.sleepSeconds(30);
    }
}
