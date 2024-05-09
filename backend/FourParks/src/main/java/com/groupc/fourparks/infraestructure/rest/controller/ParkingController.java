package com.groupc.fourparks.infraestructure.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.fourparks.application.service.ParkingServiceImpl;
import com.groupc.fourparks.infraestructure.model.dto.ParkingDto;
import com.groupc.fourparks.infraestructure.model.request.NewParkingRequest;
import com.groupc.fourparks.infraestructure.model.request.SetAdminToParkingRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/parking")
public class ParkingController {
    private final ParkingServiceImpl parkingServiceImpl;

    @PostMapping("/newParking")
    public ResponseEntity<ParkingDto> newParking(@RequestBody @Valid NewParkingRequest newParkingRequest){
        return new ResponseEntity<>(this.parkingServiceImpl.newParking(newParkingRequest), HttpStatus.OK);
    }

    @GetMapping("/getParking/{name}")
    public ResponseEntity<ParkingDto> getParking(@PathVariable(required = true) String name) {
        return new ResponseEntity<>(this.parkingServiceImpl.getParking(name), HttpStatus.OK);
    }

    @GetMapping("/getParkings")
    public ResponseEntity<List<ParkingDto>> getParkings() {
        return new ResponseEntity<>(this.parkingServiceImpl.getParkings(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteParking/{name}")
    public String deleteParking(@PathVariable(required = true) String name) {
        return this.parkingServiceImpl.deleteParking(name);
    }

    @PutMapping("/modifyParking")
    public ResponseEntity<ParkingDto> modifyParking(@RequestBody @Valid NewParkingRequest newParkingRequest){
        return new ResponseEntity<>(this.parkingServiceImpl.modifyParking(newParkingRequest), HttpStatus.OK);
    }

    @PutMapping("/setAdmin")
    public ResponseEntity<ParkingDto> putMethodName(@RequestBody @Valid SetAdminToParkingRequest setAdminToParkingRequest) {
        return new ResponseEntity<>(this.parkingServiceImpl.setAdmin(setAdminToParkingRequest), HttpStatus.OK);
    }
}
