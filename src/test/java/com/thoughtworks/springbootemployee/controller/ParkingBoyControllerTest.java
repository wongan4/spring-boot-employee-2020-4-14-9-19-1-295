package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ParkingBoyControllerTest {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    private ParkingBoyController parkingBoyController;

    @Before
    public void setup() {
        this.parkingBoyRepository = mock(ParkingBoyRepository.class);
        List<ParkingBoy> sampleParkingBoys = new ArrayList<ParkingBoy>();
        ParkingBoy parkingBoy = new ParkingBoy(1, "test", new Employee());
//        sampleParkingBoys.add(new ParkingBoy(1, "test"));
//        when(this.parkingBoyRepository.findAll())
    }
}