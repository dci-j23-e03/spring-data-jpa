package com.example.springdatajpa.service;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDepartment_ShouldReturnSavedDepartment() {
        Department department = new Department(1L, "HR", "NY", "D001");
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department savedDepartment = departmentService.saveDepartment(department);

        assertNotNull(savedDepartment);
        assertEquals(department.getId(), savedDepartment.getId());
        verify(departmentRepository, times(1)).save(department);
    }

//    @Test
//    void getDepartmentById_ShouldReturnDepartment_WhenIdExists() {
//        Department department = new Department(1L, "HR", "NY", "D001");
//        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
//
//        Department foundDepartment = departmentService.getDepartment(1L);
//
//        assertNotNull(foundDepartment);
//        assertEquals(department.getId(), foundDepartment.getId());
//        verify(departmentRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getDepartmentById_ShouldReturnNull_WhenIdDoesNotExist() {
//        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Department foundDepartment = departmentService.getDepartment(1L);
//
//        assertNull(foundDepartment);
//        verify(departmentRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getDepartmentByName_ShouldReturnDepartment() {
//        Department department = new Department(1L, "HR", "NY", "D001");
//        when(departmentRepository.findByNameCustomQuery("HR")).thenReturn(department);
//
//        Department foundDepartment = departmentService.getDepartment("HR");
//
//        assertNotNull(foundDepartment);
//        assertEquals(department.getName(), foundDepartment.getName());
//        verify(departmentRepository, times(1)).findByNameCustomQuery("HR");
//    }

//    @Test
//    void getAllDepartments_ShouldReturnListOfDepartments() {
//        List<Department> departments = Arrays.asList(
//                new Department(1L, "HR", "NY", "D001"),
//                new Department(2L, "Finance", "LA", "D002")
//        );
//        Page<Department> page = new PageImpl<>(departments);
//        when(departmentRepository.findAll(any(Pageable.class))).thenReturn(page);
//
//        List<Department> allDepartments = departmentService.getAllDepartments(1, 10, "id", "asc");
//
//        assertNotNull(allDepartments);
//        assertEquals(2, allDepartments.size());
//        verify(departmentRepository, times(1)).findAll(any(Pageable.class));
//    }
}
