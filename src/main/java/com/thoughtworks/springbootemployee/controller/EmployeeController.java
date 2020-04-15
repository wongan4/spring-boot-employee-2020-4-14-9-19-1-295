package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private Map<Integer, Employee> idEmployeeMap;

    public EmployeeController() {
        this.idEmployeeMap = new HashMap<>();
        this.idEmployeeMap.put(1, new Employee(1, "male", "default", 18));
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return new ArrayList<>(this.idEmployeeMap.values());
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return this.idEmployeeMap.get(id);
    }



    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        this.idEmployeeMap.put(employee.getId(), employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("id") int id) {
        this.idEmployeeMap.put(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") int id) {
        this.idEmployeeMap.remove(id);
    }


}
