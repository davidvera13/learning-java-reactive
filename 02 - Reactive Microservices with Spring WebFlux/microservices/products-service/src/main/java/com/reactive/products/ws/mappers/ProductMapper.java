package com.reactive.products.ws.mappers;

import com.reactive.products.ws.dto.ProductDto;
import com.reactive.products.ws.io.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // https://www.vinsguru.com/microservices-dto-to-entity-entity-to-dto-mapping-libraries-comparison/

    ProductDto entityToDto(Product product);

    Product dtoToEntity(ProductDto productDto);
}
