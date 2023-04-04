package com.example.user.controller;

import com.example.user.domain.UserDto;
import com.example.user.domain.enums.OperationType;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private RSocketRequester requester;
    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    void setup() {
        this.requester = this.builder
                .transport(TcpClientTransport
                        .create("localhost", 7071));
    }

    @Test
    void getUsers() {
        Flux<UserDto> flux = this.requester
                .route("users.get.all")
                .retrieveFlux(UserDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void getUser() {
        UserDto userDto = getRandomUser();
        Mono<UserDto> mono = this.requester
                //.route("users.get.642ad7512a77316cc2567c59")
                .route("users.get.{id}", userDto.getId())
                .retrieveMono(UserDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                //.expectNextCount(1)
                .expectNextMatches(dto -> dto.getId().equals(userDto.getId()))
                .verifyComplete();
    }

    private UserDto getRandomUser() {
        return this.requester.route("users.get.all")
                .retrieveFlux(UserDto.class)
                .next()
                .block();
    }

    @Test
    void createUser() {
        UserDto userDto = new UserDto("Jimmy Page", 14350);
        Mono<UserDto> mono = this.requester.route("users.create")
                .data(userDto)
                .retrieveMono(UserDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void updateUser() {
        UserDto userDto = getRandomUser();
        userDto.setBalance(-10);
        Mono<UserDto> mono = this.requester
                //.route("users.get.642ad7512a77316cc2567c59")
                .route("users.update.{id}", userDto.getId())
                .data(userDto)
                .retrieveMono(UserDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(mono)
                //.expectNextCount(1)
                .expectNextMatches(dto -> dto.getBalance() == - 10)
                .verifyComplete();
    }

    @Test
    void delete() throws InterruptedException {
        UserDto userDto = getRandomUser();
        Mono<Void> mono = this.requester
                //.route("users.get.642ad7512a77316cc2567c59")
                .route("users.delete.{id}", userDto.getId())
                .send();

        StepVerifier.create(mono)
                .verifyComplete();

        Thread.sleep(1000);

        // check if deleted...
        Flux<UserDto> flux = this.requester
                .route("users.get.all")
                .retrieveFlux(UserDto.class)
                .doOnNext(System.out::println);

        StepVerifier.create(flux)
                .expectNextCount(3)
                .verifyComplete();

    }
    @Test
    void headerMetadataTest()  {

        MimeType mimeType = MimeTypeUtils.parseMimeType(
                WellKnownMimeType.APPLICATION_CBOR.getString());

        UserDto dto = new UserDto();
        dto.setName("James T. Kirk");
        dto.setBalance(100);

        Mono<Void> mono = this.requester.route("users.operation.type")
                .metadata(OperationType.PUT, mimeType)
                .data(dto)
                .send();

        StepVerifier.create(mono)
                .verifyComplete();

    }
}