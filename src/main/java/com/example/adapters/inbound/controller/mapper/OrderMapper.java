package com.example.adapters.inbound.controller.mapper;

import com.example.adapters.outbound.repository.entities.Order;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.openapitools.model.OrderDto;

@UtilityClass
public class OrderMapper {

    public OrderDto map(Order order) {
        return new OrderDto()
                .id(order.getId())
                .number(order.getNumber())
                .creationDate(OffsetDateTime.from(order.getCreationDate()))
                .dateReceived(OffsetDateTime.from(order.getDateReceived()))
                .total(order.getTotal())
                .userEntity(UserMapper.toDto(order.getUserEntity()))
                .product(ProductMapper.map(order.getProduct()));

    }

    public List<OrderDto> map(List<Order> orders) {
        return orders.stream().map(OrderMapper::map).collect(Collectors.toList());
    }
}
