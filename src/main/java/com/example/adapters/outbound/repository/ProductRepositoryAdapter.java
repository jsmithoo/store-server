package com.example.adapters.outbound.repository;

import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.paramas.ProductUpdateParams;
import com.example.application.port.out.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;


    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public Optional<Product> findByNameProduct(String nameProduct) {
        return productJpaRepository.findByNameProduct(nameProduct);
    }

    @Override
    public Product saveProduct(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }



}
