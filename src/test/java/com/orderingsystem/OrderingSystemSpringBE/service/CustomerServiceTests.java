package com.billingsystem.service;

import com.billingsystem.entity.Customer;
import com.billingsystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)         // it initializes mocks and handles strict stubbings
public class CustomerServiceTests {

    /**
     * Main reference documentation features:
     * @Mock, @InjectMock
     * when(), invoke, assert...(). verify()
     *
     *** mock()/@Mock: create mock
     * optionally specify how it should behave via Answer/MockSettings
     *** when()/given() -- to specify how a mock should behave (my comment: how the concrete method should behave)
     * If the provided answers don’t fit your needs, write one yourself extending the Answer interface
     * spy()/@Spy: partial mocking, real methods are invoked but still can be verified and stubbed
     *** -@InjectMocks: automatically inject mocks/spies fields annotated with @Spy or @Mock
     *** verify(): to check methods were called with given arguments
     * can use flexible argument matching, for example any expression via the any()
     * or capture what arguments were called using @Captor instead
     */

    @Mock
    private CustomerRepository customerRepository;      // all repositories should be mocked out  = Mockito.mock(class)

    @InjectMocks
    private CustomerServiceImpl customerService;    // all service classes should inject mock - a concrete class should be given!

    private Customer customer;

    // for creating the common objects
    @BeforeEach
    public void init() {

        customer = Customer.builder()
                .name("John Johnson")
                .email("johnjohnson@gmail.com")
                .mobile("+0098-121213281")
                .id(1L)             // here the object does not receive an id from the DB due to mocking, we should give it
                .build();
    }

    @Test
    public void CustomerService_SaveCustomer_ReturnsCustomerObj() {

        // stub method call with a class object as parameter: this rule applies to the calls with any customer object
        when( customerRepository.save(any(Customer.class)) ).thenReturn(customer);      // customerRepository is mocked out, here is the rule to follow instead

        Customer savedCustomer = customerService.save(customer);        // in this case there is no significant difference between service and repo, therefore here it is only formal

        assertThat(savedCustomer).isNotNull();

        verify(customerRepository).save(any(Customer.class));       // check if save() was invoked on customerRepository with a Customer object

    }

    @Test
    public void CustomerService_GetCustomerByEmail_ReturnsCustomerObj() {

        // stub method call with concrete parameter value
        when( customerRepository.findByEmail("johnjohnson@gmail.com") ).thenReturn(customer);      // customerRepository is mocked out, here is the rule to follow instead

        Customer foundCustomer = customerService.findByEmail("johnjohnson@gmail.com");        // in this case there is no significant difference between service and repo, therefore here it is only formal

        assertThat(foundCustomer).isNotNull();

        verify(customerRepository).findByEmail("johnjohnson@gmail.com");

    }

    @Test
    public void CustomerService_DeleteCustomerById_ReturnsVoid() {

        // stub method call - but here the return type is void, therefore:   doNothing().when(instance).methodName();
        doNothing().when(customerRepository).deleteById(anyLong());      // receiving the mocked "instance", when() will know about its methods...

//        Customer foundCustomer = customerService.findByEmail("johnjohnson@gmail.com");        // in this case there is no significant difference between service and repo, therefore here it is only formal
        assertAll( () -> customerService.deleteById(customer.getId()) );        // assert with lambda due to the return type being void

        verify( customerRepository ).deleteById( anyLong() );       // check if the given method was really invoked... IMPORTANT!  >> separate row in the log...
        // at 1st try: customer did not have id (my mistake), but the test performed well!, only verify revealed that there was some problem due to the lack of id...
        // test worked well with exception... (?) - but the mocked method was not invoked - only verify revealed that...
        // verify( customerRepository, times(2) ).deleteById( anyLong() );    -  times() is necessary if the method call is in a loop! - otherwise it will throw an exc

    }

}
