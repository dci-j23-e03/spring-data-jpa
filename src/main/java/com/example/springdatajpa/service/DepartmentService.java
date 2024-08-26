package com.example.springdatajpa.service;

import com.example.springdatajpa.entity.Department;
import com.example.springdatajpa.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentService(@Autowired DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department getDepartment(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    public Department updateDepartment(Long id, Department department) {
        Department oldDepartment = departmentRepository.findById(id).orElse(null);

        if (oldDepartment != null) {
            oldDepartment.setName(department.getName());
            oldDepartment.setAddress(department.getAddress());
            oldDepartment.setCode(department.getCode());
            return departmentRepository.save(oldDepartment);
        }

        return null;
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
