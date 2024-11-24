package com.example.adapters.outbound.repository;

import com.example.adapters.outbound.repository.entities.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameProduct(String nameProduct);

    List<Product> findByNameProductContainingOrBrandProductContainingOrDescriptionProduct(
            String nameProduct, String brandProduct, String descriptionProduct);
}
