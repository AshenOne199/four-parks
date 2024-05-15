package com.groupc.fourparks.infraestructure.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.groupc.fourparks.application.service.ParkingSlotServiceImpl;
import com.groupc.fourparks.infraestructure.model.dto.ParkingSlotDto;
import com.groupc.fourparks.infraestructure.model.request.ParkingSlotRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/slots")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://fourparks.vercel.app/")
public class SlotController {
    private final ParkingSlotServiceImpl parkingSlotServiceImpl;

    @PostMapping("/slot/new")
    public ResponseEntity<ParkingSlotDto> newSlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        return new ResponseEntity<>(this.parkingSlotServiceImpl.newParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }

    @GetMapping("/slot/id/{id}")
    public ResponseEntity<ParkingSlotDto> getSlot(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlot(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List<ParkingSlotDto>> getSlotsByParking(@PathVariable(required = true) String id) {
        return new ResponseEntity<>(this.parkingSlotServiceImpl.getParkingSlotsByParking(Long.parseLong(id)), HttpStatus.OK);
    }

    @DeleteMapping("/slot/delete/id/{id}")
    public String deleteSlot(@PathVariable(required = true) String id) {
        
        return this.parkingSlotServiceImpl.deleteParkingSlot(Long.parseLong(id));
    }

    @PutMapping("/slot/update")
    public ResponseEntity<ParkingSlotDto> modifySlot(@RequestBody @Valid ParkingSlotRequest parkingSlotRequest){
        System.out.println(parkingSlotRequest.getId());
        return new ResponseEntity<>(this.parkingSlotServiceImpl.modifyParkingSlot(parkingSlotRequest), HttpStatus.OK);
    }
}
