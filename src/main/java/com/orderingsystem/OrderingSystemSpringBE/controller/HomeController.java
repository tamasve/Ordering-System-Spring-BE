package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;
    private CustomerService customerService;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public HomeController() {

    }

    @GetMapping("")
    public String home() {
        return "This is the main page";
    }


}
