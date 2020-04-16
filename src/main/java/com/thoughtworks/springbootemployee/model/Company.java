package com.thoughtworks.springbootemployee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Company {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;

    public Company(int id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
        this.employeesNumber = employees.size();
    }

    public Company() {

    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(int employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
