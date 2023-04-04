package com.example.user.controller;


import com.example.user.domain.UserDto;
import com.example.user.domain.enums.OperationType;
import com.example.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@MessageMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("get.all")
    public Flux<UserDto> getUsers() {
        return this.userService.getUsers();
    }

    @MessageMapping("get.{userId}")
    public Mono<UserDto> getUser(@DestinationVariable String userId) {
        return this.userService.getUser(userId);
    }

    @MessageMapping("create")
    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return this.userService.createUser(userDtoMono);
    }

    @MessageMapping("update.{userId}")
    public Mono<UserDto> updateUser(@DestinationVariable String userId,
                                    Mono<UserDto> userDtoMono) {
        return this.userService.updateUser(userId, userDtoMono);
    }

    @MessageMapping("operation.type")
    public Mono<Void> metadataOperationType(@Header("operation-type") OperationType operationType,
                                            Mono<UserDto> userDtoMono){
        System.out.println("***********************");
        System.out.println(operationType);
        userDtoMono.doOnNext(System.out::println).subscribe();
        userDtoMono.doOnNext(System.out::println).block();
        System.out.println("***********************");
        return Mono.empty();
    }

    @MessageMapping("delete.{userId}")
    public Mono<Void> delete(@DestinationVariable String userId) {
        System.out.println("*********************");
        System.out.println("DELETING");
        return this.userService.deleteUser(userId);
    }
}
