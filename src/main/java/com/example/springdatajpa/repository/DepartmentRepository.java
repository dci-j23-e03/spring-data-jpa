package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Department findByName(String name);
}
