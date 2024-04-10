package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Product;
import com.orderingsystem.OrderingSystemSpringBE.entity.ProductDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {

    @Query(value = "SELECT new com.billingsystem.entity.ProductDTO( p.id, p.name, p.category.name, p.unit, p.price ) FROM Product p")
    List<ProductDTO> findAllWithCatName();
}
