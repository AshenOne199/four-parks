package com.groupc.fourparks.infraestructure.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupc.fourparks.application.service.ParkingSlotServiceImpl;
import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/parking/slot")
public class ParkingSlotController {
    private final ParkingSlotServiceImpl parkingSlotServiceImpl;

    @PostMapping("/newSlot")
    public ResponseEntity<ParkingSlotDto> newSlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        return new ResponseEntity<>(this.parkingSlotServiceImpl.newParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }

    @GetMapping("/getSlot/{id}")
    public ResponseEntity<ParkingSlotDto> getSlot(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlot(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/getSlots/{id}")
    public ResponseEntity<List<ParkingSlotDto>> getSlotsByParking(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlotsByParking(Long.parseLong(id)), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSlot/{id}")
    public String deleteSlot(@PathVariable(required = true) String id) {
        
        return this.parkingSlotServiceImpl.deleteParkingSlot(Long.parseLong(id));
    }

    @PutMapping("/modifySlot")
    public ResponseEntity<ParkingSlotDto> modifySlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        System.out.println(parkingSlotRequest.getId());
        return new ResponseEntity<>(this.parkingSlotServiceImpl.modifyParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }
}
