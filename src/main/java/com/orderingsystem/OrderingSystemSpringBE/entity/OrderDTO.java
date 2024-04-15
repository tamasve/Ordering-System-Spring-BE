package com.orderingsystem.OrderingSystemSpringBE.entity;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

// a DTO class (Data Transfer Object) to create complex SQL query for orders, using multiple tables from DB

@Data
@AllArgsConstructor
public class OrderDTO {

    private Long order_id;

    @Past(message = "Date should be in the past!")
    private Date date;

    @Size(min = 3, message = "Customer name should be at least 3 characters long!")
    private String cust_name;

    @Size(min = 3, message = "Production name should be at least 3 characters long!")
    private String prod_name;

    private double price;

    @Positive(message = "Quantity of a product should be positive!")
    private double quantity;

}
