package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyBasicInfo;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @MockBean
    private CompanyRepository mockCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Company> getSampleCompanyList() {
        List<Company> mockCompanyList = new ArrayList<>();
        List<Employee> appleEmployees = new ArrayList<>();
        appleEmployees.add(new Employee(1, "male", "aabbc", 5, 100000, 1));
        List<Employee> microsoftEmployees = new ArrayList<>();
        microsoftEmployees.add(new Employee(1, "male", "default1", 18, 60, 2));
        microsoftEmployees.add(new Employee(2, "female", "default2", 19, 100, 2));
        Company apple = new Company(1, "Apple", 1, appleEmployees);
        Company microsoft = new Company(2, "Microsoft", 2, microsoftEmployees);
        mockCompanyList.add(apple);
        mockCompanyList.add(microsoft);
        return mockCompanyList;
    }

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(companyController);
        RestAssuredMockMvc.standaloneSetup(companyRepository);
        RestAssuredMockMvc.standaloneSetup(employeeRepository);
    }

    @Test
    public void should_return_all_companies_when_get_companies() {
        Mockito.when(mockCompanyRepository.findAll()).thenReturn(getSampleCompanyList());
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
        Mockito.when(mockCompanyRepository.findById(1))
                .thenReturn(getSampleCompanyList()
                        .stream()
                        .filter(company -> company.getId() == 1)
                        .findFirst());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals("Apple", company.getCompanyName());
    }

    @Test
    public void should_return_employees_when_get_employees_from_company() {
        Mockito.when(mockCompanyRepository.findById(2))
                .thenReturn(getSampleCompanyList()
                        .stream()
                        .filter(company -> company.getId() == 2)
                        .findFirst());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/2/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
    }

    @Test
    public void should_return_ok_when_add_company() {
        Company company = new Company(3, "dummy", 0, null);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .post("/companies");

        Assert.assertEquals(200, response.getStatusCode());
        Mockito.verify(this.mockCompanyRepository, Mockito.times(1)).save(company);
    }

    @Test
    public void should_return_company_page_when_get_companies_by_page() {
        Map<String, Integer> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page", 2);
        pageQueryParams.put("pageSize", 1);
        Mockito.when(mockCompanyRepository.findAll(PageRequest.of(2, 1)))
                .thenReturn(new PageImpl<Company>(getSampleCompanyList()
                        .stream()
                        .skip(1)
                        .limit(1)
                        .collect(Collectors.toList())));

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params(pageQueryParams)
                .when()
                .get("/companies");

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, companies.size());
    }

    @Test
    public void should_return_ok_when_update_company() {
        CompanyBasicInfo companyBasicInfo = new CompanyBasicInfo("dummy");
        Company targetCompany = getSampleCompanyList()
                .stream()
                .filter(company -> company.getId() == 1)
                .findFirst().orElse(null);
        assert targetCompany != null;
        Mockito.when(mockCompanyRepository.findById(1))
                .thenReturn(Optional.of(targetCompany));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(companyBasicInfo)
                .when()
                .put("/companies/1");

        targetCompany.setCompanyName("dummy");
        Mockito.verify(this.mockCompanyRepository, Mockito.times(1)).save(targetCompany);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void should_return_ok_when_delete_company() {
        Company targetCompany = getSampleCompanyList()
                .stream()
                .filter(company -> company.getId() == 1)
                .findFirst().orElse(null);
        assert targetCompany != null;
        Mockito.when(mockCompanyRepository.findById(1))
                .thenReturn(Optional.of(targetCompany));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");

        Mockito.verify(this.mockCompanyRepository, Mockito.times(1)).deleteById(1);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void should_return_company_with_employees_when_one_company_relates_many_employees() {
//        Company company = new Company(1, "big", 1, new ArrayList<>());
//        Employee employee = new Employee(1, "male", "test", 10, 0, 1);
//        this.companyRepository.save(company);
//        this.employeeRepository.save(employee);

//        MockMvcResponse response = given().contentType(ContentType.JSON)
//                .when()
//                .get("/companies");

//        System.out.println(response.getBody());

//        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
//            @Override
//            public Type getType() {
//                return super.getType();
//            }
//        });
//
//        Assert.assertEquals(1, companies.size());
//        Assert.assertEquals(1, companies.get(0).getEmployees().size());
    }

    @After
    public void clean() {
        this.employeeRepository.deleteAll();
        this.companyRepository.deleteAll();
    }
}