package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Segment;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentRepository extends ListCrudRepository<Segment, Long> {
}
