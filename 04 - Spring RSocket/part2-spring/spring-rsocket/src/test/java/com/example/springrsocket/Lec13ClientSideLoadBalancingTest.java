package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ComputationRequestDto;
import io.rsocket.loadbalance.LoadbalanceTarget;
import io.rsocket.loadbalance.RoundRobinLoadbalanceStrategy;
import io.rsocket.loadbalance.WeightedLoadbalanceStrategy;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec13ClientSideLoadBalancingTest {

    @Autowired
    private RSocketRequester.Builder builder;

    @Autowired
    private Flux<List<LoadbalanceTarget>> targets;


    @Test
    void connectionSetup() throws InterruptedException {

        // calling nginx: transport replaced by transportS
        // we can use other loadBalanceStrategy such as: WeightedLoadBalancingStrategy
        RSocketRequester requester = this.builder
                // .transports(targets, WeightedLoadbalanceStrategy.create());
                .transports(targets, new RoundRobinLoadbalanceStrategy());

        for (int i = 0; i < 50; i++) {

            requester.route("math.service.print")
                    .data(new ComputationRequestDto(i))
                    .send()
                    .subscribe();

            Thread.sleep(2000);
        }

    }
}
