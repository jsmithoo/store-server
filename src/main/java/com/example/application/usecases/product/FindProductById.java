package com.example.application.usecases.product;

import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.port.in.product.FindProductByIdQuery;
import com.example.application.port.out.ProductRepository;
import com.example.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductById implements FindProductByIdQuery {

    private final ProductRepository productRepository;

    @Override
    public Product execute(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), id.toString()));
    }
}
