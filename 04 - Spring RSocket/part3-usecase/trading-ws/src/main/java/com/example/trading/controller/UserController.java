package com.example.trading.controller;

import com.example.trading.clients.UserClient;
import com.example.trading.domain.UserDto;
import com.example.trading.domain.UserStockDto;
import com.example.trading.services.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserClient userClient;
    private final UserStockService userStockService;

    @Autowired
    public UserController(UserClient userClient, UserStockService userStockService) {
        this.userClient = userClient;
        this.userStockService = userStockService;
    }

    @GetMapping
    Flux<UserDto> getUsers() {
        return this.userClient.getUsers();
    }

    @GetMapping("{userId}/stocks")
    Flux<UserStockDto> getUsers(@PathVariable String userId) {
        return this.userStockService.getUserStocks(userId);
    }
}
