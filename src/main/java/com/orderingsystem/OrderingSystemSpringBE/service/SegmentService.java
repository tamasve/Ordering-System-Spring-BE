package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.Segment;
import com.orderingsystem.OrderingSystemSpringBE.exception.EntityNotFoundException;
import com.orderingsystem.OrderingSystemSpringBE.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SegmentService{

    SegmentRepository segmentRepository;
    @Autowired
    public SegmentService(SegmentRepository segmentRepository) { this.segmentRepository = segmentRepository;}


    public List<Segment> findAll() {
        return segmentRepository.findAll();
    }

    public Segment save(Segment segment) {
        return segmentRepository.save(segment);
    }

    public Segment update(Segment segment) {
        Long id = segment.getId();
        Segment foundSegment = findById(id);

        if (foundSegment == null)  throw new EntityNotFoundException("Segment not found with id = " + id);

        return save(segment);
    }

    public void deleteById(Long id) {
        segmentRepository.deleteById(id);
    }

    public Segment findById(Long id) {
        return segmentRepository.findById(id).orElse(null);
    }

}
