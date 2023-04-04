package com.example.user.shared.utils;

import com.example.user.domain.UserDto;
import com.example.user.io.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class UserMapper {
    public static UserDto toDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    public static UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        return userEntity;
    }
}
