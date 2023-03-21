package org.example.rsocket.commons.dto;


import lombok.*;

@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ResponseDto {
    private int input;
    private int output;
}
