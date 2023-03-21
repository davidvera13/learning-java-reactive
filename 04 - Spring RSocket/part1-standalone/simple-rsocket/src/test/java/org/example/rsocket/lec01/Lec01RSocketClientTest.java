package org.example.rsocket.lec01;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec01RSocketClientTest {
    private RSocket rSocket;

    @BeforeAll
    public void setup() {
        // Method 'setup' annotated with '@BeforeAll' should be static
        // if we add @TestInstance(TestInstance.Lifecycle.PER_CLASS), we fix issue
        // basic client creation
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();
    }


    // note: if we want to run this test, the server must be started,
    // run AppServer in package org.example.rsocket.lec01 before running test
    @Test
    public void fireAndForget() {
        Payload payload = DefaultPayload.create("Hello world!");
        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }

    // SocketAcceptorImpl > accept() called is printed one time
    @RepeatedTest(13)
    public void fireAndForgetRepeated() {
        Payload payload = DefaultPayload.create("Hello world!");
        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }

}
