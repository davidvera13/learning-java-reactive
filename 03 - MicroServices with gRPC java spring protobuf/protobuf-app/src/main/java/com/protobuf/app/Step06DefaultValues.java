package com.protobuf.app;

import com.protobuf.app.models.Client;

public class Step06DefaultValues {
    public static void main(String[] args) {
        Client client = Client.newBuilder().build();

        // client is not null !
        System.out.println("> client: " + client);
        System.out.println("> client city: " + client.getAddress().getCity());

        // has is only for objects, not for scalar types / primitives
        System.out.println("> Client has address ? " + client.hasAddress());

        System.out.println("> Client check other properties ?");

    }
}
