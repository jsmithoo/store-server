package com.example;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.adapters.outbound.repository.entities.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class DataProvidersOrder {


    public static List<Order> orderListMock(){

        System.out.println(" => Obteniedo listado de Pedidos / Mock");

        UserEntity user = new UserEntity();  // Asegúrate de proporcionar datos adecuados si no hay un constructor vacío
        Product product = new Product();     // Asegúrate de proporcionar datos adecuados si no hay un constructor vacío

        return List.of(
                new Order(1L, "001", "Coche",
                        LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40),
                        LocalDateTime.now(),
                        3,
                        BigDecimal.valueOf(15000.00), // Uso de BigDecimal en lugar de double
                        45000.00F,  // Float para el total
                        user,  // Objeto UserEntity
                        product  // Objeto Product
                ),
                new Order(2L, "002", "Moto",
                        LocalDateTime.of(2020, Month.JANUARY, 15, 10, 15, 20),
                        LocalDateTime.now(),
                        1,
                        BigDecimal.valueOf(8000.00), // Uso de BigDecimal
                        8000.00F,  // Float para el total
                        user,  // El mismo objeto UserEntity o puedes crear uno nuevo
                        product  // El mismo objeto Product o puedes crear uno nuevo
                ),
                new Order(3L, "003", "Bicicleta",
                        LocalDateTime.of(2019, Month.MARCH, 10, 8, 0, 0),
                        LocalDateTime.now(),
                        2,
                        BigDecimal.valueOf(300.00), // Uso de BigDecimal
                        600.00F,  // Float para el total
                        user,  // El mismo objeto UserEntity
                        product  // El mismo objeto Product
                )
        );
    }


    public static Optional<Order> orderMock() {

        UserEntity user = new UserEntity();
        Product product = new Product();

        return Optional.of(new Order(5L, "005", "Tabaco",
                LocalDateTime.of(2018, Month.JULY, 20, 13, 49, 55),
                LocalDateTime.now(),
                100,
                BigDecimal.valueOf(15.00),
                1500.00F,
                user,
                product)
        );
    }

    public static Order orderSaveMock() {

        UserEntity user = new UserEntity();
        Product product = new Product();

        return  new Order(4L, "004", "PC",
                LocalDateTime.of(2017, Month.JULY, 25, 15, 45, 50),
                LocalDateTime.now(),
                3,
                BigDecimal.valueOf(15000.00), // Uso de BigDecimal en lugar de double
                100.00F,  // Float para el total
                user,  // Objeto UserEntity
                product  // Objeto Product
        );
    }
}
