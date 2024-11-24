package com.example.application;

import com.example.adapters.outbound.repository.OrderJpaRepository;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.application.paramas.OrderUpdateParams;
import com.example.domain.exception.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public final OrderJpaRepository orderJpaRepository;

    public OrderService(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    public Order findById(Long id) {
        return orderJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Order.class.getSimpleName(), id.toString()));
    }

    public Order saveOrder(Order order) {
        return orderJpaRepository.save(order);
    }

    public void deleteByIdOrder(Long id) {
        orderJpaRepository.findById(id).ifPresentOrElse((order) -> orderJpaRepository.deleteById(id), () -> {
            throw new NotFoundException(Order.class.getSimpleName(), id.toString());
        });
    }

    public Order updateOrder(Long id, OrderUpdateParams orderUpdateParams) {
        Order orderToUpdate = orderJpaRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(Order.class.getSimpleName(), id.toString()));

        orderToUpdate.setNumber(orderUpdateParams.getNumber());
        orderToUpdate.setCreationDate(orderUpdateParams.getCreationDate());
        orderToUpdate.setDateReceived(orderUpdateParams.getDateReceived());
        orderToUpdate.setTotal(orderUpdateParams.getTotal());
        return orderJpaRepository.save(orderToUpdate);
    }

    public List<Order> findAllOrders(int offset, int limit) {
        Page<Order> page = orderJpaRepository.findAll(PageRequest.of(offset, limit));
        return page.getContent();
    }
}

