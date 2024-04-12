package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.Customer;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Test
    public void CustomerController_FindAllCustomers_ReturnList() throws Exception {

        List<Customer> customers = Arrays.asList(
               Customer.builder()
                       .name("Gipsz Jakab")
                       .mobile("+36309879466")
                       .email("gipszjakab@gmail.com")
                       .build(),
               Customer.builder()
                       .name("Johanna Quebec")
                       .mobile("+00956343555")
                       .email("johannaquebec@gmail.com")
                       .build()
        );

        when( customerService.findAll() ).thenReturn(customers);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[:1].email").value(customers.get(0).getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

}
