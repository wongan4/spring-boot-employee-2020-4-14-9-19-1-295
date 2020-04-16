package com.thoughtworks.springbootemployee.controller;

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

    public void temp(){
        System.out.println("hello world");
    }

    public Map<Integer, Company> getIdCompanyMap() {
        return idCompanyMap;
    }

    public void setIdCompanyMap(Map<Integer, Company> idCompanyMap) {
        this.idCompanyMap = idCompanyMap;
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
