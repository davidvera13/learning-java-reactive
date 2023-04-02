package com.example.springrsocket;

import com.example.springrsocket.shared.dto.ComputationRequestDto;
import com.example.springrsocket.shared.dto.ComputationResponseDto;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.test.StepVerifier;

@SpringBootTest
@TestPropertySource(properties = { "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.rsocket.RSocketServerAutoConfiguration" })
public class Lec15SecurityTest {

    @Autowired
    private RSocketRequester.Builder builder;

    static {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/client.truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

    }

    @Test
    void secRequestResponse() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("client", "password");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Mono<ComputationResponseDto> mono = requester
                .route("math.service.secured.square")
                // request level authorization
                .metadata(new UsernamePasswordMetadata("user", "password"), mimeType)
                .data(new ComputationRequestDto(5))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void secRequestResponseNotAuthorizedUser() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("admin", "password");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Mono<ComputationResponseDto> mono = requester
                .route("math.service.secured.square")
                // request level authorization
                .metadata(new UsernamePasswordMetadata("admin", "password"), mimeType)
                .data(new ComputationRequestDto(5))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void secRequestResponseInvalidPassword() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("client", "passwrd");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Mono<ComputationResponseDto> mono = requester
                .route("math.service.secured.square")
                // request level authorization
                .metadata(new UsernamePasswordMetadata("user", "password"), mimeType)
                .data(new ComputationRequestDto(5))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void secRequestResponseInvalidUser() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("client2", "password");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Mono<ComputationResponseDto> mono = requester
                .route("math.service.secured.square")
                // request level authorization
                .metadata(new UsernamePasswordMetadata("user", "password"), mimeType)
                .data(new ComputationRequestDto(5))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void secRequestResponseInvalidRole() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("user", "password");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Mono<ComputationResponseDto> mono = requester
                .route("math.service.secured.square")
                .data(new ComputationRequestDto(5))
                .retrieveMono(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }



    @Test
    void secRequestStream() {
        UsernamePasswordMetadata metadata = new UsernamePasswordMetadata("client", "password");
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

        RSocketRequester requester = this.builder
                // to pass header, we use meta data properties
                .setupMetadata(metadata, mimeType)
                .transport(TcpClientTransport.create("localhost", 6565));

        Flux<ComputationResponseDto> flux = requester
                .route("math.service.secured.table")
                // request level authorization
                 .metadata(new UsernamePasswordMetadata("admin", "password"), mimeType)
                .data(new ComputationRequestDto(5))
                .retrieveFlux(ComputationResponseDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
