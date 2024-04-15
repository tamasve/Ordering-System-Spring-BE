package com.orderingsystem.OrderingSystemSpringBE.service;

import com.orderingsystem.OrderingSystemSpringBE.entity.Status;
import com.orderingsystem.OrderingSystemSpringBE.exception.EntityNotFoundException;
import com.orderingsystem.OrderingSystemSpringBE.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService{

    StatusRepository statusRepository;
    @Autowired
    public StatusService(StatusRepository statusRepository) { this.statusRepository = statusRepository;}


    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public Status update(Status status) {
        Long id = status.getId();
        Status foundStatus = findById(id);

        if (foundStatus == null)  throw new EntityNotFoundException("Status not found with id = " + id);

        return save(status);
    }

    public void deleteById(Long id) {
        statusRepository.deleteById(id);
    }

    public Status findById(Long id) {
        return statusRepository.findById(id).orElse(null);
    }

}
