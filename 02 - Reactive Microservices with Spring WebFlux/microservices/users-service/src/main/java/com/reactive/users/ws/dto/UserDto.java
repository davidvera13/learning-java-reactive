package com.reactive.users.ws.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Double balance;
}
