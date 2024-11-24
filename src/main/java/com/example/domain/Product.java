package com.example.domain;
import com.example.domain.exception.MandatoryParameterException;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


@Builder
public class Product {
    private Long id;

    private String name;
    private String brand;
    private String description;
    private Float price;
    private String image;
    private Integer amount;
  
    public Product(Long id, String name, String brand, String description, Float price, String image, Integer amount) {
        if (Objects.isNull(id)){
            throw new MandatoryParameterException("id");
        }
        if (StringUtils.isBlank(name)){
            throw new MandatoryParameterException("name");
        }
        if (StringUtils.isBlank(brand)){
            throw new MandatoryParameterException("brand");
        }
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.image = image;
        this.amount = amount;
    }
}
