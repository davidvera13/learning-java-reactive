package com.reactive.products.ws.service;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.io.repository.ProductRepository;
import com.reactive.products.ws.mappers.EntitoDtoMappers;
import com.reactive.products.ws.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductMapstructService {
    ProductRepository productRepository;

    @Autowired
    public ProductMapstructService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> findAll() {
        return this.productRepository.findAll()
                //.map(product -> EntitoDtoMappers.toDto(product))
                .map(ProductMapper.INSTANCE::entityToDto);
    }

    public Mono<ProductDto> findById(String id) {
        return this.productRepository.findById(id)
                .map(ProductMapper.INSTANCE::entityToDto);
    }

    public Mono<ProductDto> insert(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(ProductMapper.INSTANCE::dtoToEntity)                   // convert dto to entity
                .flatMap(entity -> this.productRepository.insert(entity))   // storing entity and retrieving response
                .map(ProductMapper.INSTANCE::entityToDto);                  // returning entity converted to dto
        //return productDtoMono
        //        .map(dto -> ProductMapper.INSTANCE.dtoToEntity(dto))                 // convert dto to entity
        //        .flatMap(entity -> this.productRepository.insert(entity))   // storing entity and retrieving response
        //        .map(entity -> ProductMapper.INSTANCE.entityToDto(entity));             // returning entity converted to dto
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> productDtoMono) {
        return this.productRepository.findById(id)                  // the query returns an entity object
                .flatMap(entity -> productDtoMono
                        .map(ProductMapper.INSTANCE::dtoToEntity)
                        .doOnNext(productEntity -> productEntity.setId(id)))
                .flatMap(productEntity -> this.productRepository.save(productEntity))
                .map(ProductMapper.INSTANCE::entityToDto);
    }

    public Mono<Void> delete(String id) {
        return this.productRepository.deleteById(id);
    }

    public Flux<ProductDto> getByPriceRange(int min, int max) {
        return this.productRepository.findAllByPriceBetween(Range.closed(min, max))
                //.map(product -> EntitoDtoMappers.toDto(product))
                .map(ProductMapper.INSTANCE::entityToDto);
    }
}
