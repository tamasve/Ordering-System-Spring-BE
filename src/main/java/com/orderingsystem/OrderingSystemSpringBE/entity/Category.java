package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name="categories")
public class Category {

    @Id
    @Column(name = "cat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 4, message = "Category name should be at least 4 characters long!")
    @Column(nullable = false)
    String name;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    List<Product> products;

    public Category(String name) {
        this.name = name;
    }

}
