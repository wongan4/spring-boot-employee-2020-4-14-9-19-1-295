package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeById(int employeeId) {
        return this.employeeRepository.getEmployeeById(employeeId);
    }

    public List<Employee> getEmployeeInPage(int page, int pageSize) {
        List<Employee> employees = this.employeeRepository.getEmployees();
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getId))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeWithGender(String gender) {
        List<Employee> employees = this.employeeRepository.getEmployees();
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) {
        this.employeeRepository.addEmployee(employee);
    }

    public ResponseEntity<Object> updateEmployee(Employee employee, int employeeId) {
        if (this.employeeRepository.isContainsEmployeeId(employeeId)) {
            this.employeeRepository.updateEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteEmployee(int employeeId) {
        if (this.employeeRepository.isContainsEmployeeId(employeeId)) {
            this.employeeRepository.deleteEmployeeById(employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<Employee> getEmployees() {
        return this.employeeRepository.getEmployees();
    }
}
