package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.OrderDTO;
import com.orderingsystem.OrderingSystemSpringBE.entity.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderItemRepository extends ListCrudRepository<OrderItem, Long> {

// complex query for orders
    @Query(value = "SELECT new com.billingsystem.entity.OrderDTO( " +
            "oi.id, oi.order.date, oi.order.customer.name, oi.product.name, oi.product.price, oi.unit)" +
            " FROM OrderItem oi WHERE oi.id = ?1 ")
    List<OrderDTO> findAllByOrderId(Long id);
}
