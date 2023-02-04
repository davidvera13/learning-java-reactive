package com.protobuf.app;

import com.protobuf.app.models.Address;
import com.protobuf.app.models.Car;
import com.protobuf.app.models.Client;

public class Step03Composition {
    public static void main(String[] args) {
        Client client = Client.newBuilder()
                .setName("Alan")
                .setAge(25)
                .setAddress(Address.newBuilder()
                        .setPostBox(1)
                        .setStreet("Sesame Street")
                        .setCity("London")
                        .build())
                .setCar(Car.newBuilder()
                        .setMake("Ford")
                        .setModel("Mustang")
                        .setYear(1968)
                        .build())
                .build();

        System.out.println(client);
    }
}

