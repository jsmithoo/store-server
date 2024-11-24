package com.example;
import com.example.adapters.outbound.repository.entities.Product;
import com.example.application.paramas.ProductUpdateParams;
import java.util.List;
import java.util.Optional;

public class DataProviderProducts {

    public static List<Product> productListMock() {

        System.out.println(" => Obteniedo listado de Product / Mock");

        return List.of(
                new Product(1L, "Coche", 15000.00F, "Audi", "Color gris, motor 1.6", "car.png", 3),
                new Product(2L, "Zapatillas", 120.50F, "Nike", "Color Blancas", "chuck.png", 10),
                new Product(3L, "Monitor", 260.40F, "HP", "Color Negro, 28 pulgadas", "pc.png", 15),
                new Product(4L, "Reloj", 450.80F, "Citizen", "Color azul, Promaster", "clock.png", 25),
                new Product(5L, "Ordenador", 1542.00F, "MSI", "Color negro, Teclado rojo", "laptop.png", 5)
        );
    }

    public static Product productMock() {
        return Product
                .builder()
                .id(1L)
                .nameProduct("Coche")
                .priceProduct(15000.00F)
                .brandProduct("Audi")
                .descriptionProduct("Color gris, motor 1.6")
                .imageProduct("car.png")
                .amount(3)
                .build();
    }

    public static Product productSaveMock() {
        return new Product(6L, "Bicicleta", 1332.99F, "Shimano", "Color blanco", "Bici.png", 25);
    }

    public static ProductUpdateParams productUpdateParamsMock() {
        return new ProductUpdateParams("Moto", "Ducatti", "Color rojo", 1332.99F, "moto.png", 6);
    }
}
