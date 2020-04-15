package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private Map<Integer, Company> idCompanyMap;

    public CompanyController() {
        this.idCompanyMap = new HashMap<>();
        List<Employee> appleEmployees = new ArrayList<>();
        appleEmployees.add(new Employee(1, "male", "aabbc", 5, 100000));
        List<Employee> microsoftEmployee = new ArrayList<>();
        microsoftEmployee.add(new Employee(1, "male", "default1", 18, 60));
        microsoftEmployee.add(new Employee(2, "female", "default2", 19, 100));
        Company apple = new Company(1, "Apple", appleEmployees);
        Company microsoft = new Company(2, "Microsoft", microsoftEmployee);
        this.idCompanyMap.put(1, apple);
        this.idCompanyMap.put(2, microsoft);
    }

    @GetMapping
    public List<Company> getCompanies() {
        return new ArrayList<>(this.idCompanyMap.values());
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        this.idCompanyMap.put(company.getId(), company);
    }
}
