package com.javadevjournal.controller;

import com.javadevjournal.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class AppController {
    private final String URI_EMPLOYEE = "http://localhost:8081/employee";
    private final String URI_EMPLOYEE_ID = "http://localhost:8081/employee/{id}";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/v1/allEmployees")
    public ResponseEntity<List<Employee>> getAllV1() {
        Employee[] EmployeesArray = restTemplate.getForObject(URI_EMPLOYEE + "/all", Employee[].class);
        return new ResponseEntity<>(Arrays.asList(EmployeesArray), HttpStatus.OK);
    }

    @GetMapping("/v1/employees/{id}")
    public ResponseEntity<Employee> getByIdV1(@PathVariable final Integer id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        Employee Employee = restTemplate.getForObject(URI_EMPLOYEE_ID, Employee.class, params);
        return new ResponseEntity<>(Employee, HttpStatus.OK);
    }

    @GetMapping("/v2/allEmployees")
    public ResponseEntity<Employee[]> getAllV2() {
        ResponseEntity<Employee[]> responseEntity = restTemplate.getForEntity(URI_EMPLOYEE + "/all", Employee[].class);
        return responseEntity;
    }

    @GetMapping("/v2/employees/{id}")
    public ResponseEntity<Employee> getByIdV2(@PathVariable final Integer id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<Employee> responseEntity
                = restTemplate.getForEntity(URI_EMPLOYEE_ID, Employee.class, params);
        return responseEntity;
    }

    @GetMapping("/v3/allEmployees")
    public ResponseEntity<Employee[]> getAllV3() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(URI_EMPLOYEE + "/all", HttpMethod.GET, entity, Employee[].class);
    }

    @GetMapping("/v3/employees/{id}")
    public ResponseEntity<Employee> getByIdV3(@PathVariable final Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.GET, entity, Employee.class);
    }

    @PostMapping("/v1/employees")
    public ResponseEntity<Employee> createV1(@RequestBody final Employee newEmployee) {
        Employee createdEmployee = restTemplate.postForObject(URI_EMPLOYEE, newEmployee, Employee.class);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/v2/employees")
    public ResponseEntity<Employee> createV2(@RequestBody final Employee newEmployee) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<Employee>(newEmployee, httpHeaders);
        return restTemplate.exchange(URI_EMPLOYEE, HttpMethod.POST, entity, Employee.class);
    }

    @PutMapping("/v1/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeV1(@PathVariable final Integer id, @RequestBody Employee newEmployee) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        restTemplate.put(URI_EMPLOYEE_ID, newEmployee, params);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/v2/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeV2(@PathVariable final Integer id, @RequestBody Employee newEmployee) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(newEmployee, httpHeaders);
        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.PUT, entity, Employee.class);
    }

    @DeleteMapping("/v1/employees/{id}")
    public ResponseEntity<Employee> deleteV1(@PathVariable final Integer id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        restTemplate.delete(URI_EMPLOYEE_ID, params);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/v2/employees/{id}")
    public ResponseEntity<Employee> deleteV2(@PathVariable final Integer id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(URI_EMPLOYEE + id, HttpMethod.DELETE, entity, Employee.class);
    }
}