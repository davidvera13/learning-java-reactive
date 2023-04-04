package com.example.user.services;

import com.example.user.domain.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserDto> getUsers();

    Mono<UserDto> getUser(String userId);

    Mono<UserDto> createUser(Mono<UserDto> userDtoMono);

    Mono<UserDto> updateUser(String userId, Mono<UserDto> userDtoMono);

    Mono<Void> deleteUser(String userId);
}
