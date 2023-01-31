package com.reactive.products.ws.config;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class DataInit implements CommandLineRunner {
    private ProductService productService;

    public DataInit(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        ProductDto p1 = ProductDto.builder().description("4k-tv").price(1000.0).build();
        ProductDto p2 = ProductDto.builder().description("slr-camera").price(750.0).build();
        ProductDto p3 = ProductDto.builder().description("iphone").price(800.0).build();
        ProductDto p4 = ProductDto.builder().description("headphone").price(100.0).build();

        Flux.just(p1, p2, p3, p4)
                .concatWith(newProducts())
                .flatMap(p -> this.productService.insert(Mono.just(p)))
                .subscribe(System.out::println);
    }

    private Flux<ProductDto> newProducts(){
        return Flux.range(1, 50)
                .delayElements(Duration.ofMillis(125))
                .map(i -> ProductDto.builder()
                        .description("product-" + i)
                        .price(ThreadLocalRandom.current().nextDouble(10.0, 100.0))
                        .build());
    }

}
