package com.grpc.app.users.service;

import com.grpc.app.commons.models.Genre;
import com.grpc.app.users.models.UserGenreUpdateRequest;
import com.grpc.app.users.models.UserResponse;
import com.grpc.app.users.models.UserSearchRequest;
import com.grpc.app.users.models.UserServiceGrpc;
import com.grpc.app.users.repository.UserRepository;
import io.grpc.stub.StreamObserver;
//import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional // allow auto update
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.repository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    builder.setName(user.getName())
                            .setLoginId(user.getLogin())
                            .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}

