package com.employee.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.employee.entity.Employee;
import com.employee.repo.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> saveAll(List<Employee> employees) {
        return repo.saveAll(employees);
    }
}
