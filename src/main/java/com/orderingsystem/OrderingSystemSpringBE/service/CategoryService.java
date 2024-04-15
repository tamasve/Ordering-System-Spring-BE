package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll();

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);
}
