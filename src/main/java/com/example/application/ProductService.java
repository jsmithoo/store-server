package com.example.application;

import com.example.adapters.outbound.repository.ProductJpaRepository;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.paramas.ProductUpdateParams;
import com.example.domain.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public Product findById(Long id) {
        return productJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), id.toString()));
    }

    public Product findByNameProduct(String nameProduct) {
        return productJpaRepository
                .findByNameProduct(nameProduct)
                .orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), nameProduct));
    }

    public Product saveProduct(Product product) {
        return productJpaRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productJpaRepository.findById(id).ifPresentOrElse((product) -> productJpaRepository.deleteById(id), () -> {
            throw new NotFoundException(Product.class.getSimpleName(), id.toString());
        });
    }

    public Product updateProduct(Long id, ProductUpdateParams productUpdateParams) {
        Product productToUpdate = productJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Product.class.getSimpleName(), id.toString()));

        productToUpdate.setNameProduct(productUpdateParams.getNameProduct());
        productToUpdate.setBrandProduct(productUpdateParams.getBrandProduct());
        productToUpdate.setPriceProduct(productUpdateParams.getPriceProduct());
        productToUpdate.setImageProduct(productUpdateParams.getImageProduct());
        productToUpdate.setDescriptionProduct(productUpdateParams.getDescriptionProduct());
        productToUpdate.setAmount(productUpdateParams.getAmount());

        return productJpaRepository.save(productToUpdate);
    }

    public List<Product> findAllProducts(String keyword, int offset, int limit) {
        if (keyword != null && !keyword.isEmpty()) {
            return productJpaRepository.findByNameProductContainingOrBrandProductContainingOrDescriptionProduct(
                    keyword, keyword, keyword);
        }
        Page<Product> page = productJpaRepository.findAll(PageRequest.of(offset, limit));
        return page.getContent();
    }
}
