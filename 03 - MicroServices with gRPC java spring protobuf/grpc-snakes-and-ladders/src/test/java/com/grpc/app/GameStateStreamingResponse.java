package com.grpc.app;

import com.google.common.util.concurrent.Uninterruptibles;
import com.grpc.app.models.Dice;
import com.grpc.app.models.GameState;
import com.grpc.app.models.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameStateStreamingResponse implements StreamObserver<GameState> {

    private CountDownLatch latch;
    StreamObserver<Dice> diceStreamObserver;

    public GameStateStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> players = gameState.getPlayerList();
        players.forEach(player -> System.out.println(player.getName() + " is at position " + player.getPosition()));
        boolean isGameOver = players.stream().anyMatch(player -> player.getPosition() == 100);
        if(isGameOver) {
            System.out.println("Game is over");
            this.diceStreamObserver.onCompleted();
        } else {
            Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
            this.roll();
        }
        System.out.println("******************");
    }

    public void roll() {
        int diceValue = ThreadLocalRandom.current().nextInt(1, 7);
        Dice dice = Dice.newBuilder().setValue(diceValue).build();
        // return to the server ...
        this.diceStreamObserver.onNext(dice);

    }
    public void setDiceStreamObserver(StreamObserver<Dice> diceStreamObserver) {
        this.diceStreamObserver = diceStreamObserver;
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.latch.countDown();
    }
}
