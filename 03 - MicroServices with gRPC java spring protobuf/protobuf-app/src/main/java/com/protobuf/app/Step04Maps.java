package com.protobuf.app;

import com.protobuf.app.models.commons.Car;
import com.protobuf.app.models.commons.CarDealer;

public class Step04Maps {
    public static void main(String[] args) {

        CarDealer carDealer = CarDealer.newBuilder()
                .putModels(2020, Car.newBuilder()
                        .setMake("MG")
                        .setModel("TF 1250")
                        .setYear(1954)
                        .build())
                .putModels(2021, Car.newBuilder()
                        .setMake("Lotus")
                        .setModel("Seven")
                        .setYear(1958)
                        .build())
                .build();

        System.out.println(carDealer);
        System.out.println(carDealer.getModelsMap());

        Car car;

        car = carDealer.getModelsOrThrow(2021);
        System.out.println(car);

        car = carDealer.getModelsOrDefault(1000, Car.newBuilder()
                        .setMake("Ford")
                        .setModel("Sierra")
                        .setYear(1992)
                .build());
        System.out.println(car);

        car = carDealer.getModelsOrThrow(1);
        System.out.println(car);
    }
}
