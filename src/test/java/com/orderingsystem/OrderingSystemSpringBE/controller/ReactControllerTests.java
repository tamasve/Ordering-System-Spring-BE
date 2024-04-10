package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;
import com.orderingsystem.OrderingSystemSpringBE.entity.Customer;
import com.orderingsystem.OrderingSystemSpringBE.entity.ProductDTO;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

@WebMvcTest(controllers = ReactController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReactControllerTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderItemService orderItemService;


    @Test
    public void ReactController_GetAllCategories_ReturnList() throws Exception{

       Category category1 = Category.builder()
                .name("food")
                .build();
        Category category2 = Category.builder()
                .name("drink")
                .build();
        List<Category> categories = Arrays.asList(category1, category2);

        when( categoryService.findAll() ).thenReturn(categories);       // mocking

        ResultActions response = mockMvc.perform( MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                            .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(categories.size() ) ))
                            .andDo(MockMvcResultHandlers.print());      // print details
    }

    @Test
    public void ReactController_findAllProductsWithCategoryName_ReturnList() throws Exception{

        List<ProductDTO> productList = Arrays.asList(
                ProductDTO.builder()
                        .name("Milk")
                        .category("food")
                        .unit("litre")
                        .price(1)
                        .build(),
                ProductDTO.builder()
                        .name("Cheese")
                        .category("food")
                        .unit("kg")
                        .price(13)
                        .build(),
                ProductDTO.builder()
                        .name("Mouse")
                        .category("computer")
                        .unit("pcs")
                        .price(3)
                        .build()
        );

        BDDMockito.given( productService.findAllWithCatName() ).willReturn(productList);      // mocking

        ResultActions response = mockMvc.perform( MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(productList.size() ) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[:1].name").value(productList.get(0).getName() ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1:2].unit").value(productList.get(1).getUnit() ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2:3].price").value(productList.get(2).getPrice() ))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReactController_FindAllCustomers_ReturnList() throws Exception {

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
