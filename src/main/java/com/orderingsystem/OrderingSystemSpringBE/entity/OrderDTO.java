package com.orderingsystem.OrderingSystemSpringBE.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

// a DTO class (Data Transfer Object) to create complex SQL query for orders, using multiple tables from DB

@Data
@AllArgsConstructor
public class OrderDTO {

    private Long order_id;
    private Date date;
    private String cust_name;
    private String prod_name;
    private double price;
    private double unit;

}
