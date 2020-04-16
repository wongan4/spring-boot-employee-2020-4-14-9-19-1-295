package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

//    @Autowired
//    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        Map<Integer, Company> idCompanyMap = new HashMap<>();
        List<Employee> appleEmployees = new ArrayList<>();
        appleEmployees.add(new Employee(1, "male", "aabbc", 5, 100000));
        List<Employee> microsoftEmployee = new ArrayList<>();
        microsoftEmployee.add(new Employee(1, "male", "default1", 18, 60));
        microsoftEmployee.add(new Employee(2, "female", "default2", 19, 100));
        Company apple = new Company(1, "Apple", appleEmployees);
        Company microsoft = new Company(2, "Microsoft", microsoftEmployee);
        idCompanyMap.put(1, apple);
        idCompanyMap.put(2, microsoft);
        RestAssuredMockMvc.standaloneSetup(companyController);
        this.companyController.setIdCompanyMap(idCompanyMap);
    }

    @Test
    public void should_return_all_companies_when_get_companies() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("Apple", companies.get(0).getCompanyName());
    }

    @Test
    public void should_return_company_when_get_company_by_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(1, company.getId());
        Assert.assertEquals("Apple", company.getCompanyName());
    }

    @Test
    public void should_return_male_employees_when_get_employee_by_gender() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals("default1", employees.get(0).getName());
    }

    @Test
    public void should_return_ok_when_add_employee() {
        Employee employee = new Employee(10, "female", "test", 100, 0);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void should_return_employee_page_when_get_employess_by_page() {
        Map<String, Integer> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page", 2);
        pageQueryParams.put("pageSize", 2);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params(pageQueryParams)
                .when()
                .get("/employees");

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals(3, employees.get(0).getId());
        Assert.assertEquals(4, employees.get(1).getId());
    }

    @Test
    public void should_return_ok_when_update_employee() {
        Employee employee = new Employee(1, "female", "default1", 100, 0);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .put("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void should_return_ok_when_delete_employee() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");

        Assert.assertEquals(200, response.getStatusCode());
    }
}