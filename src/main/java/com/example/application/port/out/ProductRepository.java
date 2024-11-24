package com.example.application.port.out;

import com.example.adapters.outbound.repository.entities.Product;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    Optional<Product> findByNameProduct(String nameProduct);

    Product saveProduct(Product product);

    void deleteById(Long id);

}

// TODO Añadir Check Style(Formatear el codigo), Añadir el test unitario, Añadir descripciones a Swagger
