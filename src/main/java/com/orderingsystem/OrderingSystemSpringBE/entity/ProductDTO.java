package com.orderingsystem.OrderingSystemSpringBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

// a DTO class (Data Transfer Object) to create SQL query for product, using multiple tables from DB

@Data
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long product_id;
    private String name;
    private String category;
    private String unit;
    private double price;

}
