package com.github.kprusina.feature.product.mapper;

import com.github.kprusina.feature.product.Product;
import com.github.kprusina.feature.product.projection.ProductDto;
import com.github.kprusina.feature.product.request.ProductRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  Product toEntity(ProductRequest product);

  ProductDto toDto(Product product);
}
