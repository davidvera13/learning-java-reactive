package com.example.trading.services.impl;

import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.UserStockDto;
import com.example.trading.io.entity.UserStockEntity;
import com.example.trading.io.repository.UserStockRepository;
import com.example.trading.services.UserStockService;
import com.example.trading.shared.UserStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UserStockServiceImpl implements UserStockService {
    private final UserStockRepository stockRepository;

    @Autowired
    public UserStockServiceImpl(UserStockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Mono<UserStockEntity> buyStock(StockTradeRequest request) {
        return this.stockRepository
                .findUserStockEntitiesByUserIdAndStockSymbol(request.getUserId(), request.getStockSymbol())
                .defaultIfEmpty(UserStockMapper.toEntity(request))
                .doOnNext(userStockEntity -> userStockEntity.setQuantity(
                        userStockEntity.getQuantity() + request.getQuantity()))
                .flatMap(this.stockRepository::save);
    }

    @Override
    public Mono<UserStockEntity> sellStock(StockTradeRequest request) {
        return this.stockRepository
                .findUserStockEntitiesByUserIdAndStockSymbol(request.getUserId(), request.getStockSymbol())
                .filter(userStockEntity -> userStockEntity.getQuantity() >= request.getQuantity())
                .doOnNext(userStockEntity -> userStockEntity.setQuantity(
                        userStockEntity.getQuantity() - request.getQuantity()))
                .flatMap(this.stockRepository::save);
    }

    @Override
    public Flux<UserStockDto> getUserStocks(String userId) {
        return this.stockRepository.findByUserId(userId)
                .map(UserStockMapper::toUserStockDto);
    }
}
