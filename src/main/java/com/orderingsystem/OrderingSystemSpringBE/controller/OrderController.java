package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.*;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {

    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }


    @GetMapping("/order/{id}")
    @ResponseBody
    public List<OrderDTO> order(@PathVariable Long id) {
        return orderItemService.findAllByOrderId(id);
    }

    @GetMapping("/orders")
    @ResponseBody
    public List<OrderItem> orders() {
        return orderItemService.findAll();
    }

}
