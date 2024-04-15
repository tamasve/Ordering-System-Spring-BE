package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.Status;
import com.orderingsystem.OrderingSystemSpringBE.exception.EntityNotFoundException;
import com.orderingsystem.OrderingSystemSpringBE.service.StatusService;
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
@RequestMapping("/statuses")
public class StatusController {

    private StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }


    @GetMapping("")
    public List<Status> categories() {
        return statusService.findAll();
    }

    @GetMapping("/{id}")
    public Status findStatus(@PathVariable Long id) {
        Status status = statusService.findById(id);
        if (status == null)  throw new EntityNotFoundException("Status not found with id: " + id);
        return status;
    }

    @PostMapping("")
    public ResponseEntity<Status> createStatus(@Valid @RequestBody Status status) {
        Status newStatus = statusService.save(status);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( newStatus.getId() )
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ResponseEntity<Status> updateStatus(@Valid @RequestBody Status status) {
        return new ResponseEntity<>(statusService.update(status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable Long id) {
        statusService.deleteById(id);
    }

}
