package com.protobuf.app;

import com.protobuf.app.models.Television;
import com.protobuf.app.models.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Step09VersionCompatibility {
    public static void main(String[] args) throws IOException {
        // using v1
        //Television television = Television.newBuilder()
        //        .setBrand("Brandt")
        //        .setYear(2020) // should not work: we removed year
        //        .build();

        Television televisionV2 = Television.newBuilder()
                .setBrand("Sony")
                .setModel(2021) // should not work: we removed year
                .setType(Type.OLED)
                .build();


        //System.out.println(television);
        Path pathV1 = Paths.get("television-1.txt");
        /// Files.write(pathV1, television.toByteArray());
        // parsing the object updated the property key
        System.out.println("****");
        byte[] bytesV1 = Files.readAllBytes(pathV1);
        System.out.println(Television.parseFrom(bytesV1));
        // v1 didn't have type, default value is set
        System.out.println(Television.parseFrom(bytesV1).getType());

        System.out.println("Using v2 implementation" +
                "\n***********************");

        Path pathV2 = Paths.get("television-2.txt");
        Files.write(pathV2, televisionV2.toByteArray());
        // parsing the object updated the property key
        byte[] bytesV2 = Files.readAllBytes(pathV2);
        System.out.println(Television.parseFrom(bytesV2));
        // v1 didn't have type, default value is set
        System.out.println(Television.parseFrom(bytesV2).getType());
    }
}
