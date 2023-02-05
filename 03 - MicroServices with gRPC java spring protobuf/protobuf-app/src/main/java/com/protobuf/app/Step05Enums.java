package com.protobuf.app;

import com.protobuf.app.models.commons.BodyStyle;
import com.protobuf.app.models.commons.Car;
import com.protobuf.app.models.commons.CarDealer;

public class Step05Enums {
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
                        .setBodyStyle(BodyStyle.SUV)
                        .build())
                .build();

        // default value is set to 0 => here UNDEFINED is default
        System.out.println(carDealer.getModelsOrThrow(2020).getBodyStyle());
        System.out.println(carDealer.getModelsOrThrow(2021).getBodyStyle());

    }
}
