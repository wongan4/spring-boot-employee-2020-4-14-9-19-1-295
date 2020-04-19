package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyBasicInfo;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return this.companyRepository.findAll();
    }

    public Company getCompaniesById(int companyId) {
        return this.companyRepository.findById(companyId).orElse(null);
    }

    public void addCompany(Company company) {
        this.companyRepository.save(company);
    }

    public List<Employee> getEmployeesOfCompany(@PathVariable("id") int companyId) {
        Company targetCompany = this.companyRepository.findById(companyId).orElse(null);

        if (targetCompany != null) {
            return targetCompany.getEmployees();
        }
        return null;
    }

    public List<Company> getCompaniesInPage(int page, int pageSize) {
        return this.companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public ResponseEntity<Object> updateCompany(CompanyBasicInfo companyBasicInfo, int companyId) {
        Company targetCompany = this.companyRepository.findById(companyId).orElse(null);

        if (targetCompany != null) {
            targetCompany.setCompanyName(companyBasicInfo.getCompanyName());
            this.companyRepository.save(targetCompany);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> deleteCompany(int companyId) {
        Company targetCompany = this.companyRepository.findById(companyId).orElse(null);

        if (targetCompany != null) {
            this.companyRepository.deleteById(companyId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
