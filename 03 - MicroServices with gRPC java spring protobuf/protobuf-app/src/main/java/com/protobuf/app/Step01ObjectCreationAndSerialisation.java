package com.protobuf.app;

import com.protobuf.app.models.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Step01ObjectCreationAndSerialisation {
    public static void main(String[] args) throws IOException {
        // using generated claas
        Person person = Person.newBuilder()
                .setAge(22)
                .setName("Walter")
                .build();

        Person otherperson = Person.newBuilder()
                .setAge(22)
                .setName("Walter")
                .build();

        System.out.println(person);

        // equality between objects
        System.out.println(person.equals(otherperson));

        // serialization / deserialisation
        Path path = Paths.get("user.ser");
        Files.write(path, person.toByteArray());
        System.out.println("********");

        byte[] bytes = Files.readAllBytes(path);
        Person p = Person.parseFrom(bytes);
        System.out.println(p);

    }
}