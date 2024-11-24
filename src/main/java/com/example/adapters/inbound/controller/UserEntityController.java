package com.example.adapters.inbound.controller;

import com.example.adapters.inbound.controller.mapper.ProductMapper;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.adapters.outbound.repository.entities.UserEntity;
import com.example.application.UserEntityService;
import java.util.List;
import java.util.Optional;

import org.openapitools.model.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@RequestMapping(value = "/api/v1.0/users")
public class UserEntityController {

    public final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userEntityService.findById(id));
    }

    @GetMapping("/username")
    public ResponseEntity<Optional<UserEntity>> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userEntityService.findByUsername(username));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDto>> getProductsByUser(@PathVariable Long id) {
        List<Product> products = userEntityService.getProductsByUser(id);
        return ResponseEntity.ok(ProductMapper.map(products));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userEntityService.deleteUserEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
