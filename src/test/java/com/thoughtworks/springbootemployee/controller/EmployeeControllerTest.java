package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
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
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeRepository mockEmployeeRepository;

    public List<Employee> getSampleEmployeeList() {
        List<Employee> sampleEmployees = new ArrayList<>();
        sampleEmployees.add(new Employee(1, "male", "default1", 18, 60, 1));
        sampleEmployees.add(new Employee(2, "female", "default2", 19, 100,1));
        sampleEmployees.add(new Employee(3, "male", "default3", 20, 10000, 2));
        sampleEmployees.add(new Employee(4, "female", "default4", 21, 56, 2));
        sampleEmployees.add(new Employee(5, "female", "default5", 22, 0,2));
        sampleEmployees.add(new Employee(6, "male", "default6", 23, 500, 2));
        return sampleEmployees;
    }

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.employeeController);
    }

    @Test
    public void should_return_all_employees_when_get_employees() {
        Mockito.when(this.mockEmployeeRepository.findAll()).thenReturn(getSampleEmployeeList());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(6, employees.size());
        Assert.assertEquals(new Integer(1), employees.get(0).getId());
        Assert.assertEquals(new Integer(6), employees.get(5).getId());
    }

    @Test
    public void should_return_employee_when_get_employee_by_id() {
        Mockito.when(this.mockEmployeeRepository.findById(1))
                .thenReturn(getSampleEmployeeList()
                        .stream()
                        .filter(employee -> employee.getId() == 1)
                        .findFirst());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(new Integer(1), employee.getId());
    }

    @Test
    public void should_return_male_employees_when_get_employee_by_gender() {
        Mockito.when(this.mockEmployeeRepository.findAllByGender("male"))
                .thenReturn(getSampleEmployeeList()
                        .stream()
                        .filter(employee -> employee.getGender().equals("male"))
                        .collect(Collectors.toList()));

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
    }

    @Test
    public void should_return_ok_when_add_employee() {
        Employee employee = new Employee(10, "female", "test", 100, 0, 1);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(200, response.getStatusCode());
        Mockito.verify(this.mockEmployeeRepository, Mockito.times(1)).save(employee);
    }

    @Test
    public void should_return_employee_page_when_get_employess_by_page() {
        Map<String, Integer> pageQueryParams = new HashMap<>();
        pageQueryParams.put("page", 2);
        pageQueryParams.put("pageSize", 2);

        Mockito.when(this.mockEmployeeRepository.findAll(PageRequest.of(2, 2)))
                .thenReturn(new PageImpl<Employee>(getSampleEmployeeList()
                        .stream()
                        .skip(2)
                        .limit(2)
                        .collect(Collectors.toList())));

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
        Assert.assertEquals(new Integer(3), employees.get(0).getId());
        Assert.assertEquals(new Integer(4), employees.get(1).getId());
    }

    @Test
    public void should_return_ok_when_update_employee() {
        Employee employee = new Employee(1, "female", "default1", 100, 0, 1);
        Employee targetEmployee = getSampleEmployeeList()
                .stream()
                .filter(employee1 -> employee1.getId() == 1)
                .findFirst()
                .orElse(null);

        assert targetEmployee != null;
        Mockito.when(this.mockEmployeeRepository.findById(1))
                .thenReturn(Optional.of(targetEmployee));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .put("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());
        Mockito.verify(this.mockEmployeeRepository, Mockito.times(1)).save(employee);
    }

    @Test
    public void should_return_ok_when_delete_employee() {
        Employee targetEmployee = getSampleEmployeeList()
                .stream()
                .filter(employee1 -> employee1.getId() == 5)
                .findFirst()
                .orElse(null);

        assert targetEmployee != null;
        Mockito.when(this.mockEmployeeRepository.findById(5))
                .thenReturn(Optional.of(targetEmployee));
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/5");

        Assert.assertEquals(200, response.getStatusCode());
        Mockito.verify(this.mockEmployeeRepository, Mockito.times(1)).deleteById(5);
    }
}