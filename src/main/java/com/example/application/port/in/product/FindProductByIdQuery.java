package com.example.application.port.in.product;

import com.example.adapters.outbound.repository.entities.Product;

public interface FindProductByIdQuery {
    Product execute(Long id);
}
