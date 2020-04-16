package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyBasicInfo;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return this.companyService.getCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable("id") int companyId) {
        return this.companyService.getCompaniesById(companyId);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        this.companyService.addCompany(company);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesOfCompany(@PathVariable("id") int companyId) {
        return this.companyService.getEmployeesOfCompany(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesInPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return this.companyService.getCompaniesInPage(page, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCompany(@RequestBody CompanyBasicInfo companyBasicInfo, @PathVariable("id") int companyId) {
        return this.companyService.updateCompany(companyBasicInfo, companyId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable("id") int companyId) {
        return this.companyService.deleteCompany(companyId);
    }
}
