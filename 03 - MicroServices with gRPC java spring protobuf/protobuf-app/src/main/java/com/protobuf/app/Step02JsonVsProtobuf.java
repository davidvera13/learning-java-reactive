package com.protobuf.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuf.app.json.PersonJson;
import com.protobuf.app.models.Person;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Step02JsonVsProtobuf {
    public static void main(String[] args) throws IOException {
        // using generated claas
        Person proto = Person.newBuilder()
                .setAge(22)
                .setName("Walter")
                .build();

        PersonJson json = new PersonJson();
        json.setName("Walter");
        json.setAge(22);


        // using runnables
        Runnable protoRunnable = () -> {
            try {
                byte[] bytes = proto.toByteArray();
                Person protoCopy = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable jsonRunnable = () -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                byte[] bytes = mapper.writeValueAsBytes(json);
                mapper.readValue(bytes, PersonJson.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };


        Flux.range(0, 5)
                        .doOnNext(integer -> {
                            runPerformanceTest(jsonRunnable, "json");
                            runPerformanceTest(protoRunnable, "proto");
                            System.out.println("**********");
                        }).blockLast();



    }

    private static void runPerformanceTest(Runnable runnable, String operation) {
        long start = System.currentTimeMillis();
        Flux.range(1, 1_000_000)
                .doOnNext(integer -> runnable.run()).blockLast();
        long end = System.currentTimeMillis();
        System.out.println("Duration for " + operation + ": " + (end - start) + "ms");
    }
}