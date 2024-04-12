package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.*;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class HomeController {

    private CategoryService categoryService;
    private ProductService productService;
    private CustomerService customerService;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public HomeController(CategoryService categoryService, ProductService productService, CustomerService customerService,
                          OrderService orderService, OrderItemService orderItemService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/")
    public String home() {
        return "This is the main page";
    }

    @GetMapping("/categories")
    public List<Category> cats() {
        return categoryService.findAll();
    }

    @GetMapping("/products")
    public List<ProductDTO> products() {
        return productService.findAllWithCatName();
    }

    @GetMapping("/customers")
    public List<Customer> customers() {
        return customerService.findAll();
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
