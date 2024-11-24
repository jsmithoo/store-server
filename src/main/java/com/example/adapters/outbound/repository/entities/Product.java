package com.example.adapters.outbound.repository.entities;

import com.example.domain.exception.MandatoryParameterException;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String nameProduct;
    private String brandProduct;
    private String descriptionProduct;
    private Float priceProduct;
    private String imageProduct;
    private Integer amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    /* @OneToOne(mappedBy = "orderDetail")
    private OrderDetail orderDetail;*/

    public Product(
            Long id,
            String nameProduct,
            Float priceProduct,
            String brandProduct,
            String descriptionProduct,
            String imageProduct,
            Integer amount) {
        if (nameProduct == null) {
            throw new MandatoryParameterException("nameProduct");
        }
        if (brandProduct == null) {
            throw new MandatoryParameterException("brandProduct");
        }
        if (descriptionProduct == null) {
            throw new MandatoryParameterException("descriptionProduct");
        }
        if (imageProduct == null) {
            throw new MandatoryParameterException("imageProduct");
        }

        this.id = id;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.brandProduct = brandProduct;
        this.descriptionProduct = descriptionProduct;
        this.imageProduct = imageProduct;
        this.amount = amount;
    }
}
