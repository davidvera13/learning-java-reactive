package com.example.springrsocket.shared.dto;

import lombok.*;

@Data
@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ClientConnectionRequestDto {
    private String clientId;
    private String clientSecret;

}
