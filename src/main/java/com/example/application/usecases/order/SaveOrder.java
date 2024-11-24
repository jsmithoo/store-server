package com.example.application.usecases.order;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.application.port.in.order.SaveOrderQuery;
import com.example.application.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveOrder implements SaveOrderQuery {

    private final OrderRepository orderRepository;

    public Order execute(Order order){
        return orderRepository.saveOrder(order);
    }
}
