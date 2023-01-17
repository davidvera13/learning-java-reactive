package com.reactive.sec01;

import com.github.javafaker.Faker;
import com.reactive.utils.Utils;

public class Lec05FakerDemo {
    public static void main(String[] args) {
        // we may need real data to test
        for (int i = 0; i < 5; i++) {
            System.out.println(Faker.instance().name().fullName());
        }
        System.out.println("******************");
        // using Utils
        for (int i = 0; i < 5; i++) {
            System.out.println(Utils.faker().starTrek().villain());
        }
        System.out.println("******************");
        // using Utils
        for (int i = 0; i < 5; i++) {
            System.out.println(Utils.faker().starTrek().specie());
        }
        System.out.println("******************");
        // using Utils
        for (int i = 0; i < 5; i++) {
            System.out.println(Utils.faker().starTrek().location());
        }
    }
}
