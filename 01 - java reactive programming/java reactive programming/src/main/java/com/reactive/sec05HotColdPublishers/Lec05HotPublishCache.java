package com.reactive.sec05HotColdPublishers;

import com.reactive.utils.Utils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class Lec05HotPublishCache {
    // in the publisher / subscriber concept, nothing happens until the subscriber subscribes to the publisher.
    // publisher emits data to the subscriber based on the request to the subscriber
    // 2 case:
    // > Cold publisher: In case of streaming app (like netflix) every user can start to stream a movie. Each subscriber
    //                   has his own stream
    // > Hot publisher:  In case of TV channels, we all watch the same program at the same time. We have one data
    //                   producer for all the subscribers. All subscribers share the same stream
    public static void main(String[] args) {
        // cache() = publish().replay()
        Flux<String> movieStream = Flux.fromStream(Lec05HotPublishCache::getMovie)
                .delayElements(Duration.ofSeconds(1))
                .cache(2); // define number of elements stored in cache

        Utils.sleepSeconds(3);
        // a user starts "streaming" a movie
        System.out.println("--- Fox Mulder is about to join ---");
        movieStream.subscribe(Utils.subscriber("Fox Mulder"));

        Utils.sleepSeconds(7);

        // another user starts "streaming" the same movie a few seconds later
        System.out.println("--- Dana Scully is about to join ---");
        movieStream.subscribe(Utils.subscriber("Dana Scully"));

        Utils.sleepSeconds(2);

        // another user starts "streaming" the same movie a few seconds later
        System.out.println("--- Walter Skinner is about to join ---");
        movieStream.subscribe(Utils.subscriber("Walter Skinner"));
        Utils.sleepSeconds(20);
    }

    // netflix like
    private static Stream<String> getMovie() {
        System.out.println("Got the movie streaming request");
        return Stream.of(
                "Scene 1", "Scene 2", "Scene 3", "Scene 4",
                "Scene 5", "Scene 6", "Scene 7", "Scene 8",
                "Scene 9", "Scene 10", "Scene 11", "Scene 12", "Scene 13");

    }
}
