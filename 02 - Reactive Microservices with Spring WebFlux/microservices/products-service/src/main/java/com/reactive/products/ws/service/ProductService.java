package com.reactive.products.ws.service;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.io.repository.ProductRepository;
import com.reactive.products.ws.mappers.EntitoDtoMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {
    ProductRepository productRepository;

    private final Sinks.Many<ProductDto> sinks;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          Sinks.Many<ProductDto> sinks) {
        this.productRepository = productRepository;
        this.sinks = sinks;
    }

    public Flux<ProductDto> findAll() {
        return this.productRepository.findAll()
                //.map(product -> EntitoDtoMappers.toDto(product))
                .map(EntitoDtoMappers::toDto);
    }

    public Mono<ProductDto> findById(String id) {
        return this.productRepository.findById(id)
                .map(EntitoDtoMappers::toDto);
    }

    public Mono<ProductDto> insert(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(dto -> EntitoDtoMappers.toEntity(dto))                 // convert dto to entity
                .flatMap(entity -> this.productRepository.insert(entity))   // storing entity and retrieving response
                .map(entity -> EntitoDtoMappers.toDto(entity))             // returning entity converted to dto
                .doOnNext(this.sinks::tryEmitNext);
        //return productDtoMono
        //        .map(EntitoDtoMappers::toEntity)                 // convert dto to entity
        //        .flatMap(entity -> this.productRepository.insert(entity))   // storing entity and retrieving response
        //        .map(EntitoDtoMappers::toDto);             // returning entity converted to dto
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> productDtoMono) {
        //return this.productRepository.findById(id)                  // the query returns an entity object
        //        .flatMap(entity -> productDtoMono
        //                .map(dto -> EntitoDtoMappers.toEntity(dto))
        //                .doOnNext(e -> e.setId(id)))
        //        .flatMap(entity -> this.productRepository.save(entity))
        //        .map(entity -> EntitoDtoMappers.toDto(entity));

        return this.productRepository.findById(id)                  // the query returns an entity object
                .flatMap(entity -> productDtoMono
                        .map(EntitoDtoMappers::toEntity)
                        .doOnNext(productEntity -> productEntity.setId(id)))
                .flatMap(productEntity -> this.productRepository.save(productEntity))
                .map(EntitoDtoMappers::toDto);
    }

    public Mono<Void> delete(String id) {
        return this.productRepository.deleteById(id);
    }

    public Flux<ProductDto> getByPriceRange(int min, int max) {
        //return this.productRepository.findAllByPriceBetween(min, max)
        return this.productRepository.findAllByPriceBetween(Range.closed(min, max))
                //.map(product -> EntitoDtoMappers.toDto(product))
                .map(EntitoDtoMappers::toDto);
    }
}
