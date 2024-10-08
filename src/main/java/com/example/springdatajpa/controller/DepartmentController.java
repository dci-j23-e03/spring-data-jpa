package com.example.springdatajpa.controller;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.service.DepartmentService;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        // example of usage of manual validator
        Set<ConstraintViolation<Department>> constraintViolations = validateDepartment(department);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }

        Department savedDepartment = departmentService.saveDepartment(department);
        if(savedDepartment != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedDepartment.getId())
                    .toUri();
            return ResponseEntity.created(location).body(savedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(
            @RequestParam(defaultValue = "1") @Positive(message = "Needs to be positive number") Integer pageNo,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer pageSize,
            @RequestParam(defaultValue = "id") @NotBlank String sortBy,
            @RequestParam(defaultValue = "asc") @NotBlank String sortingOrder
    ) {
        if (validateSortBy(sortBy)) {
            return ResponseEntity.ok(departmentService.getAllDepartments(pageNo, pageSize, sortBy, sortingOrder));
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody ResponseEntity<Department> getDepartmentById(@PathVariable @Positive Long id) {
        Department department = departmentService.getDepartment(id);
        if (department == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(department);
        }
    }

    @GetMapping("/single/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        Department department = departmentService.getDepartment(name);
        if (department == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(department);
        }
    }

    // using @Valid annotation to trigger constraints annotation in entity
    // it is pretty much doing the same as in our manual validator
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@NotNull @PathVariable Long id, @Valid @RequestBody Department department) {
//        Objects.nonNull(department.getId());
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        if (updatedDepartment != null) {
            return ResponseEntity.ok(updatedDepartment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    private boolean validateSortBy(String sortBy) {
        List<String> validValues = new ArrayList<>(List.of("id", "name", "address", "code"));
        return validValues.contains(sortBy);
    }

//    private String sanitizeSortBy(String sortBy) {
//        Sanitizer sanitizer = new Sanitizer();
//        sanitizer.sanitize(new SanitizableData());
//    }

    // manual validation method using default validator which is triggering constraint annotations
    // this approach is needed only in case of some more complex validation, and usually not using default validator
    private Set<ConstraintViolation<Department>> validateDepartment(Department department) {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            return validator.validate(department);
        }
    }
}
