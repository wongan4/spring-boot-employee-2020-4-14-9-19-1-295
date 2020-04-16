package com.thoughtworks.springbootemployee.model;

public class CompanyBasicInfo {
    private String companyName;

    public CompanyBasicInfo(String companyName) {
        this.companyName = companyName;
    }

    public CompanyBasicInfo() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
