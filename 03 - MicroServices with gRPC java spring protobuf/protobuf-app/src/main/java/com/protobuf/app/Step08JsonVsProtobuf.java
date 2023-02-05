package com.protobuf.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuf.app.json.PersonJson;
import com.protobuf.app.models.Person;
import reactor.core.publisher.Flux;

import java.io.IOException;

public class Step08JsonVsProtobuf {
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
            long start;
            try {
                start = System.currentTimeMillis();
                byte[] bytes = proto.toByteArray();
                System.out.println("proto >" + bytes.length);
                Person protoCopy = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            System.out.println("proto > " + (end - start) + "ms");
        };

        Runnable jsonRunnable = () -> {
            long start;
            try {
                start = System.currentTimeMillis();
                ObjectMapper mapper = new ObjectMapper();
                byte[] bytes = mapper.writeValueAsBytes(json);
                System.out.println("json >" + bytes.length);
                mapper.readValue(bytes, PersonJson.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long end = System.currentTimeMillis();
            System.out.println("json > " + (end - start) + "ms");
        };


        jsonRunnable.run();
        protoRunnable.run();
    }
}