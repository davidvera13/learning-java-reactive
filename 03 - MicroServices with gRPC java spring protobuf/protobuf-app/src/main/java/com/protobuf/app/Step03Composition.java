package com.protobuf.app;

import com.protobuf.app.models.commons.Address;
import com.protobuf.app.models.commons.Car;
import com.protobuf.app.models.Client;

import java.util.ArrayList;

public class Step03Composition {
    public static void main(String[] args) {
        ArrayList<Car> cars = new ArrayList<>();
        cars.add(Car.newBuilder()
                .setMake("MG")
                .setModel("TF 1250")
                .setYear(1954)
                .build());
        cars.add(Car.newBuilder()
                .setMake("Lotus")
                .setModel("Seven")
                .setYear(1958)
                .build());



        Client client = Client.newBuilder()
                .setName("Alan")
                .setAge(25)
                .setAddress(Address.newBuilder()
                        .setPostBox(1)
                        .setStreet("Sesame Street")
                        .setCity("London")
                        .build())
                .addCars(Car.newBuilder()
                        .setMake("Ford")
                        .setModel("Mustang")
                        .setYear(1968)
                        .build())
                .addCars(Car.newBuilder()
                        .setMake("Ashton Martin")
                        .setModel("DB5")
                        .setYear(1963)
                        .build())
                .build();

        Client client2 =  Client.newBuilder()
                .setName("Quatermain")
                .setAge(20)
                .setAddress(Address.newBuilder()
                        .setPostBox(1)
                        .setStreet("Elm Street")
                        .setCity("Salem")
                        .build())
                .addAllCars(cars)
                .build();

        System.out.println(client);
        System.out.println(client2);
    }
}

