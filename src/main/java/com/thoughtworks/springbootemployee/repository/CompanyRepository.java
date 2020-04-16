package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CompanyRepository {
    private Map<Integer, Company> idCompanyMap;

    public Map<Integer, Company> getIdCompanyMap() {
        return idCompanyMap;
    }

    public void setIdCompanyMap(Map<Integer, Company> idCompanyMap) {
        this.idCompanyMap = idCompanyMap;
    }

    public List<Company> getCompanies() {
        return new ArrayList<>(this.idCompanyMap.values());
    }

    public Company getCompanyById(int companyId) {
        return this.idCompanyMap.get(companyId);
    }

    public void addCompany(Company company) {
        this.idCompanyMap.put(company.getId(), company);
    }

    public boolean isContainCompanyId(int id) {
        return this.idCompanyMap.containsKey(id);
    }
}
