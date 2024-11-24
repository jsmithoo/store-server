package com.example.application.port.in.product;

import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.paramas.ProductUpdateParams;

public interface UpdateProductQuery {

    Product execute(Long id, ProductUpdateParams productUpdateParams);
}
