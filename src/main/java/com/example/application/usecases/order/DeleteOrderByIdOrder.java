package com.example.application.usecases.order;

import com.example.adapters.outbound.repository.entities.Order;
import com.example.application.port.in.order.DeleteByIdOrderQuery;
import com.example.application.port.out.OrderRepository;
import com.example.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteOrderByIdOrder implements DeleteByIdOrderQuery {

    private OrderRepository orderRepository;

    public void execute(Long id){
        orderRepository
                .findById(id)
                .ifPresentOrElse((order) -> orderRepository.deleteById(id), () -> {
                    throw new NotFoundException(Order.class.getSimpleName(), id.toString());
                });
    }
}
