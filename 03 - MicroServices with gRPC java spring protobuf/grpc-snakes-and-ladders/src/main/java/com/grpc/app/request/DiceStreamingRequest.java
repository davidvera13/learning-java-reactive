package com.grpc.app.request;

import com.grpc.app.models.Dice;
import com.grpc.app.models.GameState;
import com.grpc.app.models.Player;
import com.grpc.app.setup.SnakesAndLaddersMap;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DiceStreamingRequest implements StreamObserver<Dice> {
    private Player client;
    private Player server;
    private StreamObserver<GameState> gameStateStreamObserver;

    public DiceStreamingRequest(Player client, Player server, StreamObserver<GameState> gameStateStreamObserver) {
        this.client = client;
        this.server = server;
        this.gameStateStreamObserver = gameStateStreamObserver;
    }

    @Override
    public void onNext(Dice dice) {
        this.client = this.getNewPlayerPosition(this.client, dice.getValue());
        if(this.client.getPosition() != 100){
            this.server = this.getNewPlayerPosition(this.server, ThreadLocalRandom.current().nextInt(1, 7));
        }
        this.gameStateStreamObserver.onNext(this.getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        this.gameStateStreamObserver.onCompleted();
    }

    private GameState getGameState(){
        return GameState.newBuilder()
                .addPlayer(this.client)
                .addPlayer(this.server)
                .build();
    }

    private Player getNewPlayerPosition(Player player, int dieValue){
        int position = player.getPosition() + dieValue;
        position = SnakesAndLaddersMap.getPosition(position);
        if(position <= 100){
            player = player.toBuilder()
                    .setPosition(position)
                    .build();
        }
        return player;
    }
}
