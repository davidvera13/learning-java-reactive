package com.reactive.products.ws.controller;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.mappers.EntitoDtoMappers;
import com.reactive.products.ws.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<ProductDto> findAll() {
        return this.productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> findById(@PathVariable("id") String id) {
        // try to throw exception
        // this.fakeRandomException();

        return this.productService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> insert(@RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.insert(productDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id, @RequestBody Mono<ProductDto> productDtoMono) {
        return this.productService.update(id, productDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return  this.productService.delete(id);
    }

    @GetMapping("/search")
    public Flux<ProductDto> getByPriceRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        return this.productService.getByPriceRange(min, max);
    }

    // we want to fake failure
    private void fakeRandomException() {
        int nextInt = ThreadLocalRandom.current().nextInt(1, 10);
        if(nextInt > 5)
            throw new RuntimeException("Ooops something went wrong...");
    }
}
