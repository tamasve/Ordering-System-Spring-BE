package com.orderingsystem.OrderingSystemSpringBE.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="customers")
public class Customer {

    @Id
    @Column(name = "cust_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 3, message = "Name should be at least 3 characters long!")
    @Column(unique = true, nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "segment_id", nullable = false)
    Segment segment;

    @Email(message = "That's not an e-mail format!")
    @Column(unique = true, nullable = false)
    String email;

    @Pattern(regexp = "^\\d{2}[-]\\d{2}[-]\\d{7,10}$", message = "That's not a phone number!")
    @Column(nullable = false)
    String mobile;

    @JsonBackReference
    @OneToMany(mappedBy = "customer")
    List<Order> orders;

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}
