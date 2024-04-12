package com.orderingsystem.OrderingSystemSpringBE.controller;

import com.orderingsystem.OrderingSystemSpringBE.entity.Category;
import com.orderingsystem.OrderingSystemSpringBE.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
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

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;


    @Test
    public void CategoryController_GetAllCategories_ReturnList() throws Exception {

        Category category1 = Category.builder()
                .name("food")
                .build();
        Category category2 = Category.builder()
                .name("drink")
                .build();
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.findAll()).thenReturn(categories);       // mocking

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(categories.size())))
                .andDo(MockMvcResultHandlers.print());      // print details
    }
}