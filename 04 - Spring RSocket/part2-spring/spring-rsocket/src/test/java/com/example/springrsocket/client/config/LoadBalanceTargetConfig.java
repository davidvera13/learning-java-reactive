package com.example.springrsocket.client.config;

import com.example.springrsocket.client.ServiceRegistryClient;
import com.example.springrsocket.serviceRegistry.RSocketServerInstance;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Configuration
public class LoadBalanceTargetConfig {
    private ServiceRegistryClient registryClient;

    @Bean
    public Flux<List<LoadbalanceTarget>> targetFlux() {
        // we provide an updated list of avalable instances
        // return Flux.from(targets());

        // faking server down
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(i -> targets())
                .doOnNext(targets -> targets.remove(ThreadLocalRandom.current().nextInt(0, 3)));
    }

    @Autowired
    public LoadBalanceTargetConfig(ServiceRegistryClient registryClient) {
        this.registryClient = registryClient;
    }

    public Mono<List<LoadbalanceTarget>> targets() {
        return Mono.fromSupplier(() -> this.registryClient.getInstances()
                .stream()
                .map(rSocketServerInstance -> LoadbalanceTarget.from(
                        uniqueKey(rSocketServerInstance),
                        transport(rSocketServerInstance)
                ))
                .collect(Collectors.toList()));

    }

    private String uniqueKey(RSocketServerInstance instance) {
        return instance.getHost() + instance.getPort();
    }

    private ClientTransport transport(RSocketServerInstance instance) {
        return TcpClientTransport.create(instance.getHost(), instance.getPort());
    }


}
