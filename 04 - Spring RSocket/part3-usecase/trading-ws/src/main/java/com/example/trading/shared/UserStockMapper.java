package com.example.trading.shared;

import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.UserStockDto;
import com.example.trading.io.entity.UserStockEntity;
import org.springframework.beans.BeanUtils;

public class UserStockMapper {
    public static UserStockEntity toEntity(StockTradeRequest request) {
        UserStockEntity userStockEntity = new UserStockEntity();
        userStockEntity.setUserId(request.getUserId());
        userStockEntity.setStockSymbol(request.getStockSymbol());
        //userStockEntity.setQuantity(request.getQuantity());
        // value is updated a second time, so we don't need to set it here for a new user
        userStockEntity.setQuantity(0);
        return userStockEntity;
    }

    public static UserStockDto toUserStockDto(UserStockEntity userStockEntity) {
        UserStockDto userStockDto = new UserStockDto();
        BeanUtils.copyProperties(userStockEntity, userStockDto);
        return userStockDto;
    }
}
