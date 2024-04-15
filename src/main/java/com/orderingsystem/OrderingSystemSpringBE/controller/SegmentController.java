package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.Segment;
import com.orderingsystem.OrderingSystemSpringBE.exception.EntityNotFoundException;
import com.orderingsystem.OrderingSystemSpringBE.service.SegmentService;
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
@RequestMapping("/segments")
public class SegmentController {

    private SegmentService segmentService;

    @Autowired
    public SegmentController(SegmentService segmentService) {
        this.segmentService = segmentService;
    }


    @GetMapping("")
    public List<Segment> categories() {
        return segmentService.findAll();
    }

    @GetMapping("/{id}")
    public Segment findSegment(@PathVariable Long id) {
        Segment segment = segmentService.findById(id);
        if (segment == null)  throw new EntityNotFoundException("Segment not found with id: " + id);
        return segment;
    }

    @PostMapping("")
    public ResponseEntity<Segment> createSegment(@Valid @RequestBody Segment segment) {
        Segment newSegment = segmentService.save(segment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( newSegment.getId() )
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ResponseEntity<Segment> updateSegment(@Valid @RequestBody Segment segment) {
        return new ResponseEntity<>(segmentService.update(segment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteSegment(@PathVariable Long id) {
        segmentService.deleteById(id);
    }

}
