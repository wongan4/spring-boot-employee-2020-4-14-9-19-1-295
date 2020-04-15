package com.thoughtworks.springbootemployee.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyBasicInfo;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable("id") int companyId) {
        return this.idCompanyMap.get(companyId);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        this.idCompanyMap.put(company.getId(), company);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesOfCompany(@PathVariable("id") int companyId) {
        return this.idCompanyMap.get(companyId).getEmployees();
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesInPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        List<Company> companies = new ArrayList<>(this.idCompanyMap.values());
        return companies.stream()
                .sorted(Comparator.comparing(Company::getId))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public void updateCompany(@RequestBody CompanyBasicInfo companyBasicInfo, @PathVariable("id") int companyId) {
        this.idCompanyMap.get(companyId).setCompanyName(companyBasicInfo.getCompanyName());
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable("id") int companyId) {
        this.idCompanyMap.get(companyId).setEmployees(new ArrayList<>());
        this.idCompanyMap.get(companyId).setEmployeesNumber(0);
    }
}
