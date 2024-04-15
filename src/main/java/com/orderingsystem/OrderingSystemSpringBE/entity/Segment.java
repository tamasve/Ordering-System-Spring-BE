package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "segments")
public class Segment {

    @Id
    @Column(name = "segment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 3, message = "Segment name should be at least 3 characters long!")
    @Column(unique = true, nullable = false)
    String name;

    @OneToMany(mappedBy = "segment")
    @JsonBackReference
    List<Customer> customerList;

    public Segment(String name) {
        this.name = name;
    }
}
