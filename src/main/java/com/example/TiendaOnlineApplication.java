package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TiendaOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaOnlineApplication.class, args);
    }
}
