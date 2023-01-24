package com.reactive.sec11Sinks.usecase;


import lombok.Data;

@Data
public class SlackMessage {
    private static final String FORMAT = "[%s -> %s] : %s";

    private String sender;
    private String receiver;
    private String message;

    @Override
    public String toString(){
        return String.format(FORMAT,
                this.sender,
                this.receiver,
                this.message);
    }

}
