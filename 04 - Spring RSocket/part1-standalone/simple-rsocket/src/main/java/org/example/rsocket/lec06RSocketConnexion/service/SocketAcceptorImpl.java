package org.example.rsocket.lec06RSocketConnexion.service;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import reactor.core.publisher.Mono;

public class SocketAcceptorImpl implements SocketAcceptor {
    @Override
    public Mono<RSocket> accept(ConnectionSetupPayload setup, RSocket sendingSocket) {
        System.out.println("SocketAcceptorImpl > accept() called");

        if(isValidClient(setup.getDataUtf8()))
            return Mono.fromCallable(MathService::new);
        else
            return Mono.fromCallable(FreeService::new);
        //Mono.fromCallable(MathService::new);
        //return Mono.fromCallable(FastProducerService::new);
    }

    private boolean isValidClient(String credentials){
        return "user:password".equals(credentials);
    }
}
