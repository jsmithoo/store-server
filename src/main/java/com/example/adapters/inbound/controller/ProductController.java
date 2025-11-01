package com.example.adapters.inbound.controller;

import com.example.adapters.inbound.controller.mapper.ProductMapper;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.apis.ProductsApiApi;
import com.example.application.ProductService;
import com.example.application.paramas.ProductUpdateParams;
import com.example.application.port.in.product.DeleteByIdQuery;
import com.example.application.port.in.product.FindByNameProductQuery;
import com.example.application.port.in.product.FindProductByIdQuery;
import com.example.application.port.in.product.SaveProductQuery;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RequestMapping(value = "/api/v1.0/products")
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApiApi {

    private final ProductService productService;

    private final FindProductByIdQuery findProductByIdQuery;

    private final FindByNameProductQuery findByNameProductQuery;

    private final SaveProductQuery saveProductQuery;

    private final DeleteByIdQuery deleteByIdQuery;


    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Permitir acceso a ADMIN y USER
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(ProductMapper.map(findProductByIdQuery.execute(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/name-product")
    public ResponseEntity<ProductDto> findByNameProduct(@RequestParam String nameProduct) {
        return ResponseEntity.ok(ProductMapper.map(findByNameProductQuery.execute(nameProduct)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        Product product = saveProductQuery.execute(ProductMapper.map(productDto));
        return new ResponseEntity<>(ProductMapper.map(product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        deleteByIdQuery.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id, @RequestBody ProductUpdateParams productUpdateParams) {
        Product product = productService.updateProduct(id, productUpdateParams);
        return ResponseEntity.ok(ProductMapper.map(product));
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAllProducts(
            @RequestParam(required = false) String keyword, @RequestParam int offset, @RequestParam int limit) {
        List<Product> products = productService.findAllProducts(keyword, offset, limit);
        return ResponseEntity.ok(ProductMapper.map(products));
    }
}

    //TODO Test unitario, dar permiso especifico a un endpoint a partir de su rol, Open api generator,(Vencido)
    //TODO utilizar dockercompose(Vencido)
    //TODO Validation del DTO
    //TODO Arreglar los metodos que no me aparezca el icono azul para que aparezca,
    //TODO poner en el TAG el UserEntity para que me aparezca en el OPENAPI,***********
    //TODO Implementar DockerFile multistage(Vencido)
    //TODO Create new database in the cloud
    //TODO manage version control in git of the current repo,*******
