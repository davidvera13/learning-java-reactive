package com.example.user.shared.utils;

import com.example.user.domain.TransactionRequest;
import com.example.user.domain.TransactionResponse;
import com.example.user.domain.UserDto;
import com.example.user.domain.enums.TransactionStatus;
import com.example.user.io.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class TransactionMapper {
    public static TransactionResponse toResponse(TransactionRequest request, TransactionStatus status) {
        TransactionResponse transactionResponse = new TransactionResponse();
        BeanUtils.copyProperties(request, transactionResponse);
        transactionResponse.setStatus(status);
        return transactionResponse;
    }

//    public static TransactionRequest toRequest(TransactionResponse response) {
//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(userDto, userEntity);
//        return userEntity;
//    }
}
