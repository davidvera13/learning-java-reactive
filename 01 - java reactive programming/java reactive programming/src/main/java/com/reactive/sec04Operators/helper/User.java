package com.reactive.sec04Operators.helper;

import com.reactive.utils.Utils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private int userId;
    private String name;

    public User(int userId) {
        this.userId = userId;
        this.name = Utils.faker().name().fullName();
    }
}
