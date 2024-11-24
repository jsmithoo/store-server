package com.example.adapters.inbound.controller.mapper;

import com.example.adapters.outbound.repository.entities.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.openapitools.model.ProductDto;

@UtilityClass
public class ProductMapper {

    public ProductDto map(Product product) {
        return new ProductDto()
                .id(product.getId())
                .nameProduct(product.getNameProduct())
                .brandProduct(product.getBrandProduct())
                .descriptionProduct(product.getDescriptionProduct())
                .priceProduct(product.getPriceProduct())
                .imageProduct(product.getImageProduct())
                .amount(product.getAmount());
    }

    public Product map(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .nameProduct(productDto.getNameProduct())
                .brandProduct(productDto.getBrandProduct())
                .descriptionProduct(productDto.getDescriptionProduct())
                .priceProduct(productDto.getPriceProduct())
                .imageProduct(productDto.getImageProduct())
                .amount(productDto.getAmount())
                .build();
    }

    public List<ProductDto> map(List<Product> products) {
        return products.stream().map(ProductMapper::map).collect(Collectors.toList());
    }
}
