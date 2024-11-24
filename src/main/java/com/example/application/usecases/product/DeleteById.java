package com.example.application.usecases.product;

import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.port.in.product.DeleteByIdQuery;
import com.example.application.port.out.ProductRepository;
import com.example.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteById implements DeleteByIdQuery {

    public final ProductRepository productRepository;

    public void execute(Long id) {
        productRepository.findById(id).ifPresentOrElse((product) -> productRepository.deleteById(id), () -> {
            throw new NotFoundException(Product.class.getSimpleName(), id.toString());
        });
    }
}
