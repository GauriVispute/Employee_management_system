package com.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
