package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Date date;

    @ManyToOne
    @JoinColumn(name = "cust_id", nullable = false)
    Customer customer;

    @JsonBackReference
    @OneToMany(mappedBy = "order")
    List<OrderItem> items;

    public Order(Date date, Customer customer) {
        this.date = date;
        this.customer = customer;
    }
}
