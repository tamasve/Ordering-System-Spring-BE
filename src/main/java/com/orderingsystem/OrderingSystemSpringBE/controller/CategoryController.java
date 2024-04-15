package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.*;
import com.orderingsystem.OrderingSystemSpringBE.exception.EntityNotFoundException;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (category == null)  throw new EntityNotFoundException("Category not found with id: " + id);
        return category;
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category newCategory = categoryService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( newCategory.getId() )
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

}
