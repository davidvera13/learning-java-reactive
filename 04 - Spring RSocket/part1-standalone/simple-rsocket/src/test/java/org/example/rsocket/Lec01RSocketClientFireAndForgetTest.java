package org.example.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.example.rsocket.commons.dto.RequestDto;
import org.example.rsocket.commons.helper.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec01RSocketClientFireAndForgetTest {
    private RSocket rSocket;
    private RSocket rSocketSlow;
    private RSocket rSocketObjects;

    @BeforeAll
    public void setup() {
        // Method 'setup' annotated with '@BeforeAll' should be static
        // if we add @TestInstance(TestInstance.Lifecycle.PER_CLASS), we fix issue
        // basic client creation
        this.rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();

        this.rSocketSlow = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6464))
                .block();

        this.rSocketObjects = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6363))
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

    @Test
    public void fireAndForgetSleep() {

        Payload payload = DefaultPayload.create("Hello world!");
        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }


    // the tests are completed before the results are displayed in server side.
    @RepeatedTest(3)
    public void fireAndForgetRepeatedSlow() {
        Payload payload = DefaultPayload.create("Hello world!");
        Mono<Void> mono = this.rSocketSlow.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }


    // we can pass objects to a payloas
    @RepeatedTest(3)
    public void fireAndForgetUsingObject() {
        Payload payload = Utils.toPayload(new RequestDto(42));
        Mono<Void> mono = this.rSocketObjects.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }

    // if we don't map it to object, we pass a json
    @RepeatedTest(3)
    public void fireAndForgetUsingObjectAlternative() {
        Payload payload = Utils.toPayload(new RequestDto(42));
        Mono<Void> mono = this.rSocket.fireAndForget(payload);

        StepVerifier
                .create(mono)
                .verifyComplete();
    }

}
