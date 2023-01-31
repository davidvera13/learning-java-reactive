package com.reactive.users.ws.service;

import com.reactive.users.ws.dto.UserDto;
import com.reactive.users.ws.io.repository.UserRepository;
import com.reactive.users.ws.mappers.EntityDtoMappers;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserDto> findAll() {
        return this.userRepository.findAll()
                // .map(usersEntity ->EntityDtoMappers.userEntityToDto(usersEntity));
                .map(EntityDtoMappers::userEntityToDto);
    }

    public Mono<UserDto> findById(long userId) {
        return this.userRepository.findById(userId)
                .map(EntityDtoMappers::userEntityToDto);
    }

    public Mono<UserDto> save(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(EntityDtoMappers::userDtoToEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDtoMappers::userEntityToDto);
    }

    public Mono<UserDto> update(long id, Mono<UserDto> userDtoMono) {
        return this.userRepository.findById(id) // we find the userEntity stored and map it as entity. then save
                .flatMap(usersEntity -> userDtoMono
                        .map(EntityDtoMappers::userDtoToEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDtoMappers::userEntityToDto);
    }

    public Mono<Void> deleteById(long id) {
        return this.userRepository.deleteById(id);
    }
}
