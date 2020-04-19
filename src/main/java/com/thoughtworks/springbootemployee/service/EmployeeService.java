package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeById(int employeeId) {
        return this.employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getEmployeeInPage(int page, int pageSize) {
        return this.employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public List<Employee> getEmployeeWithGender(String gender) {
        return this.employeeRepository.findAllByGender(gender);
    }

    public void addEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    public ResponseEntity<Object> updateEmployee(Employee employee, int employeeId) {
        Employee targetEmployee = this.employeeRepository.findById(employeeId).orElse(null);

        if (targetEmployee != null) {
            this.employeeRepository.save(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteEmployee(int employeeId) {
        Employee targetEmployee = this.employeeRepository.findById(employeeId).orElse(null);

        if (targetEmployee != null) {
            this.employeeRepository.deleteById(employeeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }
}
