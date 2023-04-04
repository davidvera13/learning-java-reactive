package com.example.user.services.impl;

import com.example.user.domain.UserDto;
import com.example.user.io.repository.UserRepository;
import com.example.user.services.UserService;
import com.example.user.shared.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Flux<UserDto> getUsers() {
        return this.userRepository.findAll()
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> getUser(final String userId) {
        return this.userRepository.findById(userId)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono.map(UserMapper::toEntity)
                //.flatMap(userEntity -> this.userRepository.save(userEntity))
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<UserDto> updateUser(String userId, Mono<UserDto> userDtoMono) {
        return this.userRepository.findById(userId)
                .flatMap(userEntity -> userDtoMono.map(UserMapper::toEntity)
                        .doOnNext(currentUserEntity -> currentUserEntity.setId(userId)))
                .flatMap(this.userRepository::save)
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<Void> deleteUser(String userId) {
        return this.userRepository.deleteById(userId);
    }
}
