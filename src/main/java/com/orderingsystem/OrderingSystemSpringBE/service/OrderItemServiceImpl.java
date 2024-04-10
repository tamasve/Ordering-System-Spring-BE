package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.OrderDTO;
import com.orderingsystem.OrderingSystemSpringBE.entity.OrderItem;
import com.orderingsystem.OrderingSystemSpringBE.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    private OrderItemRepository orderItemRepository;
    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {this.orderItemRepository = orderItemRepository;}

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderDTO> findAllByOrderId(Long id) {
        return orderItemRepository.findAllByOrderId(id);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id);
    }
}
