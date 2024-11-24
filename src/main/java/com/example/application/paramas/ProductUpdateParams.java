package com.example.application.paramas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductUpdateParams {
    private String nameProduct;
    private String brandProduct;
    private String descriptionProduct;
    private Float priceProduct;
    private String imageProduct;
    private Integer amount;
}
