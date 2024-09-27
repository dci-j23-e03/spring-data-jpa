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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest({DepartmentController.class, DepartmentService.class})
public class DepartmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;
    private Long departmentId1;
    private Long departmentId2;

    @BeforeEach
    void setUp() {
        departmentRepository.deleteAll();
        Department department1 = new Department(1L, "HR", "NY", "D001");
        Department department2 = new Department(2L, "Finance", "NY", "D001");
        List<Department> savedDepartments = departmentRepository.saveAll(Arrays.asList(department1, department2));
        departmentId1 = savedDepartments.get(0).getId();
        departmentId2 = savedDepartments.get(1).getId();
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
                .andExpect(jsonPath("$[0].id").value(departmentId1))
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].id").value(departmentId2))
                .andExpect(jsonPath("$[1].name").value("Finance"));
    }
}
