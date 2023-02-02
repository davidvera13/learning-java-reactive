package com.reactive.products.ws.controller;

import com.reactive.products.ws.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products/stream")
public class ProductStreamController {
    private final Flux<ProductDto> flux;


    @Autowired
    public ProductStreamController(Flux<ProductDto> flux) {
        this.flux = flux;
    }

    // this endpoint emits a flux of ProductDto to the web interface when a new product is created
    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<ProductDto> getUpdates() {
        return this.flux;
    }


    @GetMapping(
            value = "/{maxPrice}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<ProductDto> getUpdatesByMaxPrice(@PathVariable int maxPrice) {
        return this.flux.filter(productDto -> productDto.getPrice() < maxPrice);
    }

}
