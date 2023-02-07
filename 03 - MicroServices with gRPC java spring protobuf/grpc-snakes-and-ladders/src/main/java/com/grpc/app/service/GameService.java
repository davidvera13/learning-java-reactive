package com.grpc.app.service;

import com.grpc.app.models.Dice;
import com.grpc.app.models.GameServiceGrpc;
import com.grpc.app.models.GameState;
import com.grpc.app.models.Player;
import com.grpc.app.request.DiceStreamingRequest;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Dice> roll(StreamObserver<GameState> responseObserver) {
        //return super.roll(responseObserver);
        // we need two plauers
        Player client = Player.newBuilder().setName("client").setPosition(0).build();
        Player server = Player.newBuilder().setName("server").setPosition(0).build();
        return new DiceStreamingRequest(client, server, responseObserver);
    }
}
