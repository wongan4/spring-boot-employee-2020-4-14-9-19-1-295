package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees() {
        return this.employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") int employeeId) {
        return this.employeeService.getEmployeeById(employeeId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeInPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return this.employeeService.getEmployeeInPage(page, pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeWithGender(@RequestParam("gender") String gender) {
        return this.employeeService.getEmployeeWithGender(gender);
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        this.employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee, @PathVariable("id") int employeeId) {
        return this.employeeService.updateEmployee(employee, employeeId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int employeeId) {
        return this.employeeService.deleteEmployee(employeeId);
    }


}
