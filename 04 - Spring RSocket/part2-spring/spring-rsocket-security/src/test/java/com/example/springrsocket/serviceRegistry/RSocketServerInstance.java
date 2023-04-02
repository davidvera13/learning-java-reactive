package com.example.springrsocket.serviceRegistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor @NoArgsConstructor
public class RSocketServerInstance {
    private String host;
    private int port;
}
