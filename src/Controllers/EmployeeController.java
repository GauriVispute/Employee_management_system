package com.employee.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.employee.entity.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("/saveAll")
    public List<Employee> saveAll(@RequestBody List<Employee> employees) {
        return service.saveAll(employees);
    }
}
