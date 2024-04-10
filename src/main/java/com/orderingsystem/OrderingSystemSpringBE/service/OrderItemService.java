package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.OrderDTO;
import com.orderingsystem.OrderingSystemSpringBE.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    List<OrderItem> findAll();

    List<OrderDTO> findAllByOrderId(Long id);

    Optional<OrderItem> findById(Long id);
}
