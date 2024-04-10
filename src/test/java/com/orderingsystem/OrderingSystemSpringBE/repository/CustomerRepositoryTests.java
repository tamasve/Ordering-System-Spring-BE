package com.orderingsystem.OrderingSystemSpringBE.repository;

import com.orderingsystem.OrderingSystemSpringBE.entity.Customer;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


// -- ASSERTJ --

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void CustomerRepository_Save_ReturnSavedCustomer() {

        // Arrange
        Customer customer = Customer.builder()
                .name("John Johnson")
                .email("johnjohnson@gmail.com")
                .mobile("+0098-121213281")
                .build();

        // Act
        Customer savedCustomer = customerRepository.save(customer);

        // Assert
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isGreaterThan(0);

    }

    @Test
    public void CustomerRepository_findByEmail_FindCustomerByEmail() {

        Customer customer = Customer.builder()
                .name("Gil Gould")
                .email("gilgould@gmail.com")
                .mobile("+0089-98811555")
                .build();
        Customer savedCustomer = customerRepository.save(customer);

        Customer foundByEmail = customerRepository.findByEmail("gilgould@gmail.com");
        assertThat( foundByEmail.getName() ).isEqualTo( savedCustomer.getName() );
    }

    @Test
    public void CustomerRepository_Delete_ReturnCustomerIsEmpty() {

        Customer customer = Customer.builder()
                .name("Hugh Berry")
                .email("hberry@gmail.com")
                .mobile("+0079-98454325")
                .build();
        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.deleteById(savedCustomer.getId());

        Customer foundById = customerRepository.findById(savedCustomer.getId()).orElse(null);
        assertThat( foundById ).isNull();
    }

}
