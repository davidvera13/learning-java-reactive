package com.example.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private float balance;

    public UserDto(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }
}
