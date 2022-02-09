package com.javadevjournal.controller;

import com.javadevjournal.model.Employee;
import com.javadevjournal.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.isPresent() ? ResponseEntity.ok(employee.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(savedEmployee.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()) {
            emp.get().setName(employee.getName());
            emp.get().setSalary(employee.getSalary());
            employeeRepository.save(emp.get());
            return ResponseEntity.ok(emp.get());
        }
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
