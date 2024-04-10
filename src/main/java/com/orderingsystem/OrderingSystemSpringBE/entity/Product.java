package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    String name;

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
