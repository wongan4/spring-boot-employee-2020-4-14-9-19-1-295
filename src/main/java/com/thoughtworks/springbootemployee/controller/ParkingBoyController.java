package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {

    @Autowired
    private ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoy> getAllParkingBoys() {
        return this.parkingBoyService.findAllParkingBoy();
    }

    @PostMapping
    public ResponseEntity<Object> addParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        return this.parkingBoyService.addParkingBoy(parkingBoy);
    }
}
