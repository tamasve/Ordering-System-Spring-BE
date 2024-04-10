package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Long> {
}
