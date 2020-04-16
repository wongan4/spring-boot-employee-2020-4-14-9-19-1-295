package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyBasicInfo;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return this.companyRepository.getCompanies();
    }

    public Company getCompaniesById(int companyId) {
        return this.companyRepository.getCompanyById(companyId);
    }

    public void addCompany(Company company) {
        this.companyRepository.addCompany(company);
    }

    public List<Employee> getEmployeesOfCompany(@PathVariable("id") int companyId) {
        return this.companyRepository.getCompanyById(companyId).getEmployees();
    }

    public List<Company> getCompaniesInPage(int page, int pageSize) {
        List<Company> companies = new ArrayList<>(this.companyRepository.getCompanies());
        return companies.stream()
                .sorted(Comparator.comparing(Company::getId))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void updateCompany(CompanyBasicInfo companyBasicInfo, int companyId) {
        this.companyRepository.getCompanyById(companyId).setCompanyName(companyBasicInfo.getCompanyName());
    }

    public void deleteCompany(int companyId) {
        this.companyRepository.getCompanyById(companyId).setEmployees(new ArrayList<>());
        this.companyRepository.getCompanyById(companyId).setEmployeesNumber(0);
    }
}
