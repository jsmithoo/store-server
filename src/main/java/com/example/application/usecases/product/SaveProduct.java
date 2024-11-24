package com.example.application.usecases.product;

import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.port.in.product.SaveProductQuery;
import com.example.application.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveProduct implements SaveProductQuery {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Product product) {
        return productRepository.saveProduct(product);
    }
}
