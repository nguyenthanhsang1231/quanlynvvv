package com.example.quanlynhanvien.repository;

import com.example.quanlynhanvien.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainsIgnoreCase(String name);

    List<Employee> findAllByOrderBySalaryAsc();
}
