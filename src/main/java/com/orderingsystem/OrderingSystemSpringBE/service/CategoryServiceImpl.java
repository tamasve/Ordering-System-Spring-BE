package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;
import com.orderingsystem.OrderingSystemSpringBE.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository;}


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

}
