package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Status;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends ListCrudRepository<Status, Long> {
}
