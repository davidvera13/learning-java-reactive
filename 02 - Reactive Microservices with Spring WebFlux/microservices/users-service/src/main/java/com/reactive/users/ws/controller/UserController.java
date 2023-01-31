package com.reactive.users.ws.controller;

import com.reactive.users.ws.dto.TransactionResponseDto;
import com.reactive.users.ws.dto.UserDto;
import com.reactive.users.ws.service.TransactionService;
import com.reactive.users.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TransactionService transactionService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public Flux<UserDto> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable("id") long userId) {
        return this.userService.findById(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // @PostMapping
    // public Mono<UserDto> save(@RequestBody Mono<UserDto> userDtoMono) {
    //    return userService.save(userDtoMono);
    //}

    @PostMapping
    public Mono<ResponseEntity<UserDto>> save(@RequestBody Mono<UserDto> userDtoMono) {
        return userService.save(userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> update(@PathVariable long id, @RequestBody Mono<UserDto> userDtoMono) {
        return userService.update(id, userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") long userId) {
        return this.userService.deleteById(userId);
    }

    @GetMapping("/{id}/transactions")
    public Flux<TransactionResponseDto> get(@PathVariable("id") long userId) {
        return this.transactionService.findAllByUserId(userId);
    }
}
