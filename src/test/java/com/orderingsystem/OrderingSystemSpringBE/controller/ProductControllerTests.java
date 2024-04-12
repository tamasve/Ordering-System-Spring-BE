package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.ProductDTO;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
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


@WebMvcTest(controllers = HomeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void ProductController_findAllProductsWithCategoryName_ReturnList() throws Exception{

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


}
