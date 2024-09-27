package com.example.springdatajpa.controller;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDepartmentById_ShouldReturnDepartment_WhenIdExists() throws Exception {
        // Arrange
        Department department = new Department(1L, "HR", "NY", "D001");
        when(departmentService.getDepartment(1L)).thenReturn(department);

        // Act & Assert
        mockMvc.perform(get("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("HR"))
                .andExpect(jsonPath("$.address").value("NY"))
                .andExpect(jsonPath("$.code").value("D001"));

        verify(departmentService, times(1)).getDepartment(1L);
    }

    @Test
    void getDepartmentById_ShouldReturn404_WhenIdDoesNotExist() throws Exception {
        // Arrange
        when(departmentService.getDepartment(1L)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(departmentService, times(1)).getDepartment(1L);
    }
}