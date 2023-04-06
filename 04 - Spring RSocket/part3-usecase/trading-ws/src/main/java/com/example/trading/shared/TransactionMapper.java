package com.example.trading.shared;

import com.example.trading.domain.StockTradeRequest;
import com.example.trading.domain.StockTradeResponse;
import com.example.trading.domain.TransactionRequest;
import com.example.trading.domain.enums.TradingStatus;
import com.example.trading.domain.enums.TradingType;
import com.example.trading.domain.enums.TransactionType;
import org.springframework.beans.BeanUtils;

public class TransactionMapper {
    public static TransactionRequest toTransactionRequest(StockTradeRequest request, float amount) {
        TransactionRequest transactionRequest = new TransactionRequest();
        TransactionType type = TradingType.BUY.equals(request.getTradingType()) ?
                TransactionType.DEBIT :
                TransactionType.CREDIT;
        transactionRequest.setType(type);
        transactionRequest.setUserId(request.getUserId());
        transactionRequest.setAmount(amount);
        return transactionRequest;
    }

    public static StockTradeResponse toTradeResponse(StockTradeRequest request,
                                                     TradingStatus status,
                                                     float price) {
        StockTradeResponse tradeResponse = new StockTradeResponse();
        BeanUtils.copyProperties(request, tradeResponse);
        tradeResponse.setTradingStatus(status);
        tradeResponse.setPrice(price);
        return tradeResponse;
    }

}
