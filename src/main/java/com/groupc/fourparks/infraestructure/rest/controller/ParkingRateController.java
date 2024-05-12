package com.groupc.fourparks.infraestructure.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.groupc.fourparks.application.service.ParkingRateServiceImpl;
import com.groupc.fourparks.infraestructure.model.dto.ParkingRateDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingRateRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/parking/rate")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class ParkingRateController {
    private final ParkingRateServiceImpl parkingRateServiceImpl;

    @PostMapping("/newRate")
    public ResponseEntity<ParkingRateDto> newSlot(@RequestBody @Valid ParkingRateRequest parkingRateRequest){
        return new ResponseEntity<>(this.parkingRateServiceImpl.newParkingRate(parkingRateRequest), HttpStatus.OK);
    }

    @GetMapping("/getRate/{id}")
    public ResponseEntity<ParkingRateDto> getSlot(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingRateServiceImpl.getParkingRate(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/getRates/{id}")
    public ResponseEntity<List<ParkingRateDto>> getSlotsByParking(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingRateServiceImpl.getParkingRatesByParking(Long.parseLong(id)), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRate/{id}")
    public String deleteSlot(@PathVariable(required = true) String id) {
        return this.parkingRateServiceImpl.deleteParkingRate(Long.parseLong(id));
    }

    @PutMapping("/modifyRate")
    public ResponseEntity<ParkingRateDto> modifySlot(@RequestBody @Valid ParkingRateRequest parkingRateRequest){
        return new ResponseEntity<>(this.parkingRateServiceImpl.modifyParkingRate(parkingRateRequest), HttpStatus.OK);
    }
}
