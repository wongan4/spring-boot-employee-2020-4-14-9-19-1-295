package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeeController);
    }

    @Test
    public void should_return_all_employees_when_get_employees() {
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
        Assert.assertEquals(1, employees.get(0).getId());
        Assert.assertEquals(6, employees.get(5).getId());
    }

    @Test
    public void should_return_employee_when_get_employee_by_id() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(1, employee.getId());
        Assert.assertEquals("default1", employee.getName());
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
        Employee employee = new Employee(1, "male", "default1", 100, 0);
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