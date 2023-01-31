package com.reactive.users.ws.mappers;

import com.reactive.users.ws.dto.TransactionRequestDto;
import com.reactive.users.ws.dto.TransactionResponseDto;
import com.reactive.users.ws.dto.UserDto;
import com.reactive.users.ws.dto.enums.TransactionStatus;
import com.reactive.users.ws.io.entity.UserTransactionsEntity;
import com.reactive.users.ws.io.entity.UsersEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoMappers {
    public static UsersEntity userDtoToEntity(UserDto userDto) {
        UsersEntity entity = new UsersEntity();
        BeanUtils.copyProperties(userDto, entity);
        return entity;
    }

    public static UserDto userEntityToDto(UsersEntity user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static UserTransactionsEntity transactionDtoToUserTransactionEntity(TransactionRequestDto requestDto) {
        UserTransactionsEntity userTransactionsEntity = new UserTransactionsEntity();
        userTransactionsEntity.setUserId(requestDto.getUserId());
        userTransactionsEntity.setAmount(requestDto.getAmount());
        userTransactionsEntity.setTransactionDate(LocalDateTime.now());
        return userTransactionsEntity;
    }

    // we could use entity object here instead of dto
    public static TransactionResponseDto transactionRequestDtoToTransactionResponseDto(TransactionRequestDto requestDto, TransactionStatus status) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setAmount(requestDto.getAmount());
        transactionResponseDto.setUserId(requestDto.getUserId());
        transactionResponseDto.setStatus(status);
        return transactionResponseDto;
    }

    public static TransactionResponseDto transactionEntityToTransactionDto(UserTransactionsEntity userTransactionsEntity) {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setAmount(userTransactionsEntity.getAmount());
        transactionResponseDto.setUserId(userTransactionsEntity.getUserId());
        transactionResponseDto.setStatus(TransactionStatus.APPROVED);
        // see above
        return transactionResponseDto;
    }
}
