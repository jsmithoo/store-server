package com.example.application.usecases.order;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.application.port.in.order.FindOrderByIdQuery;
import com.example.application.port.out.OrderRepository;
import com.example.application.usecases.product.FindProductById;
import com.example.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOrderById implements FindOrderByIdQuery {

    private final OrderRepository orderRepository;

    public Order execute(Long id){
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Order.class.getSimpleName(), id.toString()));
    }
}
