package com.protobuf.app;

import com.protobuf.app.models.Person;

public class Main {
    public static void main(String[] args) {
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
    }
}