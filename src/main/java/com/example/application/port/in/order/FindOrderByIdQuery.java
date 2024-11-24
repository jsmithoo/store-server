package com.example.application.port.in.order;

import com.example.adapters.outbound.repository.entities.Order;

public interface FindOrderByIdQuery {

    Order execute(Long id);
}
