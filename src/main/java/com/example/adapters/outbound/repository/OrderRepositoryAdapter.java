package com.example.adapters.outbound.repository;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.application.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Optional<Order> findById(Long id){
        return orderJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        orderJpaRepository.deleteById(id);
    }

    @Override
    public Order saveOrder(Order order){
        return orderJpaRepository.save(order);
    }
}
