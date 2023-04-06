package com.example.trading.clients;

import com.example.trading.config.RSocketClientConfig;
import com.example.trading.domain.TransactionRequest;
import com.example.trading.domain.TransactionResponse;
import com.example.trading.domain.UserDto;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private RSocketRequester requester;

    @Autowired
    public UserClient(RSocketRequester.Builder builder,
                      RSocketConnectorConfigurer connectorConfigurer,
                      @Value("${users.services.host}") String host,
                      @Value("${users.services.port}") int port) {
        this.requester = builder.rsocketConnector(connectorConfigurer)
                .transport(TcpClientTransport.create(host, port));
    }

    public Mono<TransactionResponse> doTransaction(TransactionRequest transactionRequest) {
        return this.requester.route("users.transactions")
                .data(transactionRequest)
                .retrieveMono(TransactionResponse.class)
                .doOnNext(System.out::println);
    }

    public Flux<UserDto> getUsers() {
        return this.requester.route("users.get.all")
                .retrieveFlux(UserDto.class);
    }
}
