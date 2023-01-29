package com.reactive.products.ws.mappers;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.io.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntitoDtoMappers {
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
