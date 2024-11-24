package com.example.application.port.out;

import com.example.adapters.outbound.repository.entities.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    void deleteById(Long id);

    Order saveOrder(Order order);
}
