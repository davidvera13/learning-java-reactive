package com.reactive.products.ws.config;

import com.reactive.products.ws.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinksConfig {

    @Bean
    public Sinks.Many<ProductDto> sink() {
        return Sinks.many().replay().limit(1);
    }

    @Bean
    public Flux<ProductDto> productDtoBroadcast(Sinks.Many<ProductDto> sink) {
        return sink.asFlux();
    }
}
