package com.orderingsystem.OrderingSystemSpringBE.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order_items")
public class OrderItem {

    @Id
    @Column(name = "oi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    Product product;

    @Column(nullable = false)
    double unit;

    public OrderItem(Order order, Product product, double unit) {
        this.order = order;
        this.product = product;
        this.unit = unit;
    }
}
