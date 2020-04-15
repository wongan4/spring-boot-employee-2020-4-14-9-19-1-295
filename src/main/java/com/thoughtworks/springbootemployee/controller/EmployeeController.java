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
        this.idEmployeeMap.put(1, new Employee(1, "male", "default1", 18, 60));
        this.idEmployeeMap.put(2, new Employee(2, "female", "default2", 19, 100));
        this.idEmployeeMap.put(3, new Employee(3, "male", "default3", 20, 10000));
        this.idEmployeeMap.put(4, new Employee(4, "female", "default4", 21, 56));
        this.idEmployeeMap.put(5, new Employee(5, "female", "default5", 22, 0));
        this.idEmployeeMap.put(6, new Employee(6, "male", "default6", 23, 500));
    }

    @GetMapping
    public List<Employee> getEmployees() {
        return new ArrayList<>(this.idEmployeeMap.values());
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable("id") int employeeId) {
        return this.idEmployeeMap.get(employeeId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeeInPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        List<Employee> employees = new ArrayList<>(this.idEmployeeMap.values());
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeWithGender(@RequestParam("gender") String gender) {
        List<Employee> employees = new ArrayList<>(this.idEmployeeMap.values());
        return employees.stream()
                .filter(employee -> employee.getGender().equals("male"))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addEmployee(@RequestBody Employee employee) {
        this.idEmployeeMap.put(employee.getId(), employee);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable("id") int employeeId) {
        this.idEmployeeMap.put(employeeId, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") int employeeId) {
        this.idEmployeeMap.remove(employeeId);
    }


}
