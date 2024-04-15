package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {

    @Id
    @Column(name = "prod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 3, message = "Product name should be at least 3 characters long!")
    @Column(nullable = false)
    String name;

    @Pattern(regexp = "[A-Za-z]{3,10}", message = "Unit name should contain only letters!")
    @Column(nullable = false)
    String unit;

    @Column(nullable = false)
    double price;

    @ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
    Category category;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    List<OrderItem> orderItems;

    public Product(String name, String unit, double price, Category category) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.category = category;
    }
}
