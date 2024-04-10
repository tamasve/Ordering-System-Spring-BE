package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.Product;
import com.orderingsystem.OrderingSystemSpringBE.entity.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<ProductDTO> findAllWithCatName();
}
