package com.example.adapters.inbound.controller;

import com.example.adapters.inbound.controller.mapper.OrderMapper;
import com.example.adapters.outbound.repository.entities.Order;
import com.example.apis.OrderControllerApi;
import com.example.application.OrderService;
import com.example.application.paramas.OrderUpdateParams;
import java.util.List;

import com.example.application.port.in.order.DeleteByIdOrderQuery;
import com.example.application.port.in.order.FindOrderByIdQuery;
import com.example.application.port.in.order.SaveOrderQuery;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.OrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1.0/orders")
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerApi {

    private final OrderService orderService;

    private final FindOrderByIdQuery findOrderByIdQuery;

    private final DeleteByIdOrderQuery deleteByIdOrderQuery;

    private final SaveOrderQuery saveOrderQuery;

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        return ResponseEntity.ok(findOrderByIdQuery.execute(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByIdOrder(@PathVariable Long id) {
        deleteByIdOrderQuery.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO Estudiar Test de Integracion(Que es y que tipo de clase se puede utilizar en este test)

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long id, @RequestBody OrderUpdateParams orderUpdateParams) {
        Order order = orderService.updateOrder(id, orderUpdateParams);
        return ResponseEntity.ok(OrderMapper.map(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrders(int offset, int limit) {
        List<Order> orders = orderService.findAllOrders(offset, limit);
        return ResponseEntity.ok(OrderMapper.map(orders));
    }
}
