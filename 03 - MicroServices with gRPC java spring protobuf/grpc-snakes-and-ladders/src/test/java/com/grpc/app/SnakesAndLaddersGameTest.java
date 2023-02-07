package com.grpc.app;

import com.grpc.app.models.Dice;
import com.grpc.app.models.GameServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SnakesAndLaddersGameTest {
    // async
    private GameServiceGrpc.GameServiceStub stub;

    @BeforeAll
    void setup() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 6789)
                .usePlaintext()
                .build();

        this.stub = GameServiceGrpc.newStub(channel);
    }

    @Test
    void playerGame() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        // starting game
        GameStateStreamingResponse gameStateStreamingResponse = new GameStateStreamingResponse(latch);
        StreamObserver<Dice> diceStreamObserver = this.stub.roll(gameStateStreamingResponse);
        gameStateStreamingResponse.setDiceStreamObserver(diceStreamObserver);
        gameStateStreamingResponse.roll();
        latch.await();

    }
}
