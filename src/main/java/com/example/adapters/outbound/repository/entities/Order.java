package com.example.adapters.outbound.repository.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String number;
    private String name;
    private LocalDateTime creationDate;
    private LocalDateTime dateReceived;
    private Integer amount;
    private BigDecimal price;
    private Float total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
