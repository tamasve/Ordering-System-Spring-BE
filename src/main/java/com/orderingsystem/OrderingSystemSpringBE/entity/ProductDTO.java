package com.orderingsystem.OrderingSystemSpringBE.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(min = 3, message = "Product name should be at least 3 characters long!")
    private String name;

    @Size(min = 3, message = "Category name should be at least 3 characters long!")
    private String category;

    @Pattern(regexp =  "[A-Za-z]{3,10}", message = "Unit name should contain only letters!")
    private String unit;

    private double price;

}
