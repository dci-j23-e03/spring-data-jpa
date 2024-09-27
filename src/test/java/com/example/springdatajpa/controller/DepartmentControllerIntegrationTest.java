package com.example.springdatajpa.controller;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
        Department department1 = new Department(1L, "HR", "NY", "D001");
        Department department2 = new Department(2L, "Finance", "NY", "D001");
        departmentRepository.saveAll(Arrays.asList(department1, department2));
    }

    @Test
    void getAllDepartments_ShouldReturnListOfDepartments() throws Exception {
        mockMvc.perform(get("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNo", "1")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortingOrder", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Finance"));
    }
}
