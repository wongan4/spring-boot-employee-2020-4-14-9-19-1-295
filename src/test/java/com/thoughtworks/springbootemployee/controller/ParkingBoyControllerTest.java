package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.mock;

public class ParkingBoyControllerTest {

    @Autowired
    private ParkingBoyController parkingBoyController;

    @MockBean
    private ParkingBoyRepository mockParkingBoyRepository;

    public List<ParkingBoy> getSampleParkingBoyList() {
        List<ParkingBoy> sampleParkingBoyList = new ArrayList<>();
        sampleParkingBoyList.add(new ParkingBoy(1, "test1", 1, null));
        sampleParkingBoyList.add(new ParkingBoy(2, "test2", 2, null));
        return sampleParkingBoyList;
    }

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.parkingBoyController);
    }

    @Test
    public void should_return_all_parking_boy_when_get_parking_boys() {
        Mockito.when(this.mockParkingBoyRepository.findAll()).thenReturn(getSampleParkingBoyList());
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys");

        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals(6, parkingBoys.size());
    }

    @Test
    public void should_return_ok_when_add_parking_boy() {
        ParkingBoy parkingBoy = new ParkingBoy(3, "test3", 3, null);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(parkingBoy)
                .when()
                .post("/parking-boys");


        Assert.assertEquals(200, response.getStatusCode());
        Mockito.verify(this.mockParkingBoyRepository, Mockito.times(1)).save(parkingBoy);
    }
}