package com.example.springdatajpa.controller;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.service.DepartmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDepartment.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDepartment);
    }

    @GetMapping
    public List<Department> getAllDepartments(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortingOrder
    ) {
        return departmentService.getAllDepartments(pageNo, pageSize, sortBy, sortingOrder);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @GetMapping("/single/{name}")
    public Department getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartment(name);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@NotNull @PathVariable Long id, @Valid @RequestBody Department department) {
//        Objects.nonNull(department.getId());
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
