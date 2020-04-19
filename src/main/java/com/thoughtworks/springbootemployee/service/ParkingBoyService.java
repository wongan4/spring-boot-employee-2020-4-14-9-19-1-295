package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> findAllParkingBoy() {
        return this.parkingBoyRepository.findAll();
    }

    public ResponseEntity<Object> addParkingBoy(ParkingBoy parkingBoy) {
        this.parkingBoyRepository.save(parkingBoy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
